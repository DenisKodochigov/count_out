package com.example.count_out.ui.screens.executor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.count_out.R
import com.example.count_out.data.DataRepository
import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.entity.CommandService
import com.example.count_out.entity.DataForServ
import com.example.count_out.entity.DataForUI
import com.example.count_out.entity.MessageApp
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.TickTime
import com.example.count_out.service_count_out.CountOutServiceBind
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExecuteWorkViewModel @Inject constructor(
    private val messageApp: MessageApp,
    private val dataRepository: DataRepository,
    private val serviceBind: CountOutServiceBind
): ViewModel() {
    private val _executeWorkoutScreenState = MutableStateFlow(
        ExecuteWorkoutScreenState(
            startWorkOutService = {
                dataForServ.training.value = it
                commandService(CommandService.START_WORK) },
            stopWorkOutService = { commandService(CommandService.STOP_WORK) },
            pauseWorkOutService = { commandService(CommandService.PAUSE_WORK) },
            updateSet = { trainingId, setDB -> updateSet(trainingId, setDB) }
        ))
    val executeWorkoutScreenState: StateFlow<ExecuteWorkoutScreenState> =
        _executeWorkoutScreenState.asStateFlow()

    private var dataForServ = DataForServ()
    private val isBindService: MutableStateFlow<Boolean> = MutableStateFlow(false)

    init {
        initServiceApp()
        connectToStoredBleDev()
    }
    private fun initServiceApp(){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                if ( !serviceBind.isBound.value ) serviceBind.bindService()
                dataForServ.enableSpeechDescription =
                    MutableStateFlow(dataRepository.getSetting(R.string.speech_description).value == 1)
            }.fold(
                onSuccess = { },
                onFailure = { messageApp.errorApi("initServiceApp ${it.message ?: ""}") }
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            serviceBind.isBound.collect { isBound->
                if (isBound) {
                    kotlin.runCatching {
                        commandService( CommandService.START_SERVICE )
                        serviceBind.service.getDataForUi()
                    }.fold(
                        onSuccess = { receiveState(it) },
                        onFailure = { messageApp.errorApi("initServiceApp1 ${it.message ?: ""}") }
                    )
                }
            }
        }
    }
    private fun commandSrv(command: CommandService){
        serviceBind.service.commandService( command, dataForServ)
    }
    private fun commandService(command: CommandService){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { commandSrv( command) }.fold(
                onSuccess = { },
                onFailure = { messageApp.errorApi("initServiceApp ${it.message ?: ""}") }
            )
        }
    }

    fun getTraining(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { dataRepository.getTraining(id) }.fold(
                onSuccess = {
                    _executeWorkoutScreenState.update { currentState -> currentState.copy( training = it ) }
                    dataForServ.training.value = it
                },
                onFailure = { messageApp.errorApi(it.message ?: "") }
            )
        }
    }

    private fun updateSet(trainingId: Long, set: SetDB) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { dataRepository.updateSet(trainingId, set) }.fold(
                onSuccess = {
                    _executeWorkoutScreenState.update { currentState ->
                        currentState.copy(training = it, playerSet = set) }
                    dataForServ.training.value = it
                },
                onFailure = { messageApp.errorApi(it.message ?: "") }
            )
        }
    }

    private fun connectToStoredBleDev() {
        viewModelScope.launch(Dispatchers.IO) {
            dataRepository.getBleDevStoreFlow().collect{ device->
                if (device.address.isNotEmpty()) {
                    dataForServ.addressForSearch = device.address
                    commandSrv( CommandService.CONNECT_DEVICE)
                }
            }
        }
    }

    private fun receiveState(dataForUI: DataForUI){
        viewModelScope.launch(Dispatchers.IO) {
            dataForUI.runningState.collect{
                dataForServ.runningState.value = it
                _executeWorkoutScreenState.update { currentState ->
                    currentState.copy(stateWorkOutService = it) }
                if (it == RunningState.Stopped) {
                    dataForServ = DataForServ()
                    _executeWorkoutScreenState.update { currentState ->
                        currentState.copy(
                            startTime = 0L,
                            tickTime = TickTime(hour = "00", min = "00", sec = "00"),
                            messageWorkout = emptyList(),
                        )
                    }
                    return@collect
                }
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            dataForUI.set.collect{ set->
                _executeWorkoutScreenState.update { currentState -> currentState.copy( playerSet = set )}
                if (executeWorkoutScreenState.value.stateWorkOutService == RunningState.Stopped) return@collect}
        }
        viewModelScope.launch(Dispatchers.IO) {
            dataForUI.nextSet.collect{ set->
                _executeWorkoutScreenState.update { currentState ->
                    currentState.copy(
                        listActivity = if (set != null ) currentState.activityList(set.idSet)
                        else currentState.listActivity)}
                if (executeWorkoutScreenState.value.stateWorkOutService == RunningState.Stopped) return@collect}
        }
        viewModelScope.launch(Dispatchers.IO) {
            dataForUI.flowTick.collect { tick ->
                _executeWorkoutScreenState.update { currentState -> currentState.copy( tickTime = tick )}
                if (executeWorkoutScreenState.value.stateWorkOutService == RunningState.Stopped) return@collect}
        }
        viewModelScope.launch(Dispatchers.IO) {
            dataForUI.durationSpeech.collect { duration ->
                dataRepository.updateDuration(duration)}
        }
        viewModelScope.launch(Dispatchers.IO) {
            dataForUI.message.collect { message ->
                message?.let { mes->
                    _executeWorkoutScreenState.update { currentState ->
                        currentState.copy( messageWorkout = currentState.addMessage(mes) ) }
                    if (executeWorkoutScreenState.value.stateWorkOutService == RunningState.Stopped) return@collect}
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            dataForUI.lastConnectHearthRateDeviceF.collect { lastHR ->
                if (dataForUI.runningState.value == RunningState.Stopped) return@collect
                _executeWorkoutScreenState.update { currentState ->
                    currentState.copy(lastConnectHearthRateDevice = lastHR) }
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            dataForUI.connectingStateF.collect { state ->
                if (dataForUI.runningState.value == RunningState.Stopped) return@collect
                _executeWorkoutScreenState.update { currentState ->
                    currentState.copy( connectingState = state,) }
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            dataForUI.heartRateF.collect { hr ->
                if (dataForUI.runningState.value == RunningState.Stopped) return@collect
                _executeWorkoutScreenState.update { currentState ->
                    currentState.copy(heartRate = hr) }
            }
        }
    }
}
