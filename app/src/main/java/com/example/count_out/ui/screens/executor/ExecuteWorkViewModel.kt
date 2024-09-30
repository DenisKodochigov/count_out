package com.example.count_out.ui.screens.executor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.count_out.R
import com.example.count_out.data.DataRepository
import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.entity.MessageApp
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.SendToService
import com.example.count_out.entity.SendToUI
import com.example.count_out.entity.TickTime
import com.example.count_out.entity.Training
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
            startWorkOutService = { startWork(it) },
            stopWorkOutService = { stopWork() },
            pauseWorkOutService = { pauseWork() },
            updateSet = { trainingId, setDB -> updateSet(trainingId, setDB) }
        ))
    val executeWorkoutScreenState: StateFlow<ExecuteWorkoutScreenState> =
        _executeWorkoutScreenState.asStateFlow()

    private var sendToService = SendToService()

    init {
        initServiceApp()
        connectToStoredBleDev()
    }
    private fun initServiceApp(){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                if ( !serviceBind.isBound.value ) {
                    serviceBind.bindService()
                    serviceBind.service.startCountOutService(sendToService)
                } else null
            }.fold(
                onSuccess = { it?.let { senUI ->  receiveState(senUI)}},
                onFailure = { messageApp.errorApi("initServiceApp ${it.message ?: ""}") }
            )
        }
    }

    private fun startWork(training: Training){
        if (sendToService.runningState.value == RunningState.Paused) goonWork()
        else  startWorkOut(training)
    }
    private fun startWorkOut(training: Training) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                sendToService.training = MutableStateFlow(training)
                sendToService.runningState = MutableStateFlow(RunningState.Started )
                sendToService.enableSpeechDescription =
                    MutableStateFlow(dataRepository.getSetting(R.string.speech_description).value == 1)
                serviceBind.service.startWork()
            }.fold(
                onSuccess = {  },
                onFailure = { messageApp.errorApi(it.message ?: "") }
            )
        }
    }
    private fun goonWork() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { serviceBind.service.goonWork() }.fold(
                onSuccess = { },
                onFailure = { messageApp.errorApi(it.message ?: "") }
            )
        }
    }
    private fun pauseWork(){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { serviceBind.service.pauseWork() }.fold(
                onSuccess = { _executeWorkoutScreenState.update { currentState ->
                        currentState.copy( stateWorkOutService =
                            serviceBind.service.sendToUI.runningState.value)
                    }
                },
                onFailure = { messageApp.errorApi(it.message ?: "") }
            )
        }
    }
    private fun stopWork(){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { serviceBind.service.stopWorkSignal() }.fold(
                onSuccess = {
                    _executeWorkoutScreenState.update { currentState ->
                        currentState.copy(
                            startTime = 0L,
                            tickTime = TickTime(hour = "00", min = "00", sec = "00"),
                            messageWorkout = emptyList(),
//                            stateWorkOutService = service.sendToService(),
                        )
                    }
                },
                onFailure = { messageApp.errorApi(it.message ?: "") }
            )
        }
    }

    fun getTraining(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { dataRepository.getTraining(id) }.fold(
                onSuccess = {
                    _executeWorkoutScreenState.update { currentState -> currentState.copy( training = it ) }
                    sendToService.training.value = it
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
                    sendToService.training.value = it
                },
                onFailure = { messageApp.errorApi(it.message ?: "") }
            )
        }
    }

    private fun connectToStoredBleDev() {
        viewModelScope.launch(Dispatchers.IO) {
            dataRepository.getBleDevStoreFlow().collect{
                if (it.address.isNotEmpty()) {
                    sendToService.addressForSearch = it.address
                    serviceBind.service.connectDevice()
                }
            }
        }
    }

    private fun receiveState(sendToUI: SendToUI){
        viewModelScope.launch(Dispatchers.IO) {
            sendToUI.runningState.collect{
                sendToService.runningState.value = it
                _executeWorkoutScreenState.update { currentState -> currentState.copy(stateWorkOutService = it) }
                if (it == RunningState.Stopped) {
                    sendToService = SendToService()
                    return@collect
                }
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            sendToUI.set.collect{ set->
                _executeWorkoutScreenState.update { currentState -> currentState.copy( playerSet = set )}
                if (executeWorkoutScreenState.value.stateWorkOutService == RunningState.Stopped) return@collect}
        }
        viewModelScope.launch(Dispatchers.IO) {
            sendToUI.nextSet.collect{ set->
                _executeWorkoutScreenState.update { currentState ->
                    currentState.copy(
                        listActivity = if (set != null ) currentState.activityList(set.idSet)
                        else currentState.listActivity)}
                if (executeWorkoutScreenState.value.stateWorkOutService == RunningState.Stopped) return@collect}
        }
        viewModelScope.launch(Dispatchers.IO) {
            sendToUI.flowTick.collect { tick ->
                _executeWorkoutScreenState.update { currentState ->
                    currentState.copy( tickTime = tick )}
                if (executeWorkoutScreenState.value.stateWorkOutService == RunningState.Stopped) return@collect}
        }
        viewModelScope.launch(Dispatchers.IO) {
            sendToUI.durationSpeech.collect { duration ->
                dataRepository.updateDuration(duration)}
        }
        viewModelScope.launch(Dispatchers.IO) {
            sendToUI.message.collect { message ->
                message?.let { mes->
                    _executeWorkoutScreenState.update { currentState ->
                        currentState.copy( messageWorkout = currentState.addMessage(mes) ) }
                    if (executeWorkoutScreenState.value.stateWorkOutService == RunningState.Stopped) return@collect}
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            sendToUI.lastConnectHearthRateDeviceF.collect { lastHR ->
                if (sendToUI.runningState.value == RunningState.Stopped) return@collect
                _executeWorkoutScreenState.update { currentState ->
                    currentState.copy(lastConnectHearthRateDevice = lastHR) }
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            sendToUI.connectingStateF.collect { state ->
                if (sendToUI.runningState.value == RunningState.Stopped) return@collect
                _executeWorkoutScreenState.update { currentState ->
                    currentState.copy( connectingState = state,) }
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            sendToUI.heartRateF.collect { hr ->
                if (sendToUI.runningState.value == RunningState.Stopped) return@collect
                _executeWorkoutScreenState.update { currentState ->
                    currentState.copy(heartRate = hr) }
            }
        }
    }
}
