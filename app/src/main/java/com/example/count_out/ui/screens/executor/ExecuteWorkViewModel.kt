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
import com.example.count_out.ui.view_components.lg
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

    private val dataForServ = DataForServ()

    init {
        initServiceApp()
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
                        connectToStoredBleDev()
                        serviceBind.service.getDataForUi()
                    }.fold(
                        onSuccess = { receiveState( it ) },
                        onFailure = { messageApp.errorApi("initServiceApp1 ${it.message ?: ""}") }
                    )
                }
            }
        }
    }
    private suspend fun commandSrv(command: CommandService){
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
                    lg("connectToStoredBleDev ${device.address}")
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
                _executeWorkoutScreenState.update { currentState -> currentState.copy(stateWorkOutService = it) }
                if (it == RunningState.Stopped) {
                    _executeWorkoutScreenState.update { currentState ->
                        currentState.copy(
                            startTime = 0L,
                            tickTime = TickTime(hour = "00", min = "00", sec = "00"),
                            playerSet = null,
                            messageWorkout = emptyList(),
                            listActivity = emptyList()
                        )
                    }
                    return@collect
                }
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            dataForUI.set.collect{ set->
                if (dataForUI.runningState.value == RunningState.Stopped) return@collect
                _executeWorkoutScreenState.update { currentState -> currentState.copy( playerSet = set )}}
        }
        viewModelScope.launch(Dispatchers.IO) {
            dataForUI.nextSet.collect{ set->
                if (dataForUI.runningState.value == RunningState.Stopped) return@collect
                _executeWorkoutScreenState.update { currentState ->
                    currentState.copy(
                        listActivity = if (set != null ) currentState.activityList(set.idSet)
                        else currentState.listActivity)}}
        }
        viewModelScope.launch(Dispatchers.IO) {
            dataForUI.flowTick.collect { tick ->
                if (dataForUI.runningState.value == RunningState.Stopped) return@collect
                _executeWorkoutScreenState.update { currentState -> currentState.copy( tickTime = tick )}}
        }
        viewModelScope.launch(Dispatchers.IO) {
            dataForUI.durationSpeech.collect { duration ->
                if (dataForUI.runningState.value == RunningState.Stopped) return@collect
                dataRepository.updateDuration(duration)}
        }
        viewModelScope.launch(Dispatchers.IO) {
            dataForUI.message.collect { message ->
                if (dataForUI.runningState.value == RunningState.Stopped) return@collect
                message?.let { mes ->
                    _executeWorkoutScreenState.update { currentState ->
                        currentState.copy(messageWorkout = currentState.addMessage(mes)) } } }
        }
        viewModelScope.launch(Dispatchers.IO) {
            dataForUI.lastConnectHearthRateDevice.collect { lastHR ->
                if (dataForUI.runningState.value == RunningState.Stopped) return@collect
                _executeWorkoutScreenState.update { currentState ->
                    currentState.copy(lastConnectHearthRateDevice = lastHR) } }
        }
        viewModelScope.launch(Dispatchers.IO) {
            dataForUI.connectingState.collect { state ->
                if (dataForUI.runningState.value == RunningState.Stopped) return@collect
                _executeWorkoutScreenState.update { currentState ->
                    currentState.copy( connectingState = state,) } }
        }
        viewModelScope.launch(Dispatchers.IO) {
            dataForUI.heartRate.collect { hr ->
                if (dataForUI.runningState.value == RunningState.Stopped) return@collect
                _executeWorkoutScreenState.update { currentState ->
                    currentState.copy(heartRate = hr) } }
        }
    }
}
