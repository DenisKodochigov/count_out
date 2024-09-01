package com.example.count_out.ui.screens.play_workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.count_out.R
import com.example.count_out.data.DataRepository
import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.entity.ErrorApp
import com.example.count_out.entity.SendToUI
import com.example.count_out.entity.SendToWorkService
import com.example.count_out.entity.StateRunning
import com.example.count_out.entity.TickTime
import com.example.count_out.entity.Training
import com.example.count_out.entity.bluetooth.SendToBle
import com.example.count_out.service.bluetooth.BleManager
import com.example.count_out.service.workout.ServiceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayWorkoutViewModel @Inject constructor(
    private val errorApp: ErrorApp,
    private val dataRepository: DataRepository,
    private val bleManager: BleManager,
    private val serviceManager: ServiceManager,
): ViewModel() {
    private val _playWorkoutScreenState = MutableStateFlow(
        PlayWorkoutScreenState(
            startWorkOutService = { startWorkOutService(it) },
            stopWorkOutService = { stopWorkOutService() },
            pauseWorkOutService = { pauseWorkOutService() },
            updateSet = { trainingId, setDB -> updateSet(trainingId, setDB) }
        ))
    val playWorkoutScreenState: StateFlow<PlayWorkoutScreenState> =
        _playWorkoutScreenState.asStateFlow()
    private val sendToBle = SendToBle()
    private var sendToWorkService = SendToWorkService()

    init {
        startBleService()
        connectToStoredBleDev()
    }

    fun getTraining(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { dataRepository.getTraining(id) }.fold(
                onSuccess = {
                    _playWorkoutScreenState.update { currentState -> currentState.copy( training = it ) }
                    sendToWorkService.training.value = it
                },
                onFailure = { errorApp.errorApi(it.message!!) }
            )
        }
    }

    private fun updateSet(trainingId: Long, set: SetDB) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { dataRepository.updateSet(trainingId, set) }.fold(
                onSuccess = {
                    _playWorkoutScreenState.update { currentState ->
                        currentState.copy(training = it, playerSet = set) }
                    sendToWorkService.training.value = it
                },
                onFailure = { errorApp.errorApi(it.message!!) }
            )
        }
    }

    private fun startBleService(){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { bleManager.startBleService(sendToBle) }.fold(
                onSuccess = { receiveDevicesUIBleService(it)},
                onFailure = { errorApp.errorApi(it.message!!) }
            )
        }
    }
    private suspend fun receiveDevicesUIBleService(sendToUI: MutableStateFlow<SendToUI>){
        viewModelScope.launch(Dispatchers.IO) {
            sendToUI.collect { send ->
                _playWorkoutScreenState.update { currentState ->
                    currentState.copy(
                        lastConnectHearthRateDevice = send.lastConnectHearthRateDevice,
                        connectingDevice = send.connectingDevice,
                        heartRate = send.heartRate,)
                }
            }
        }
    }
    private fun connectToStoredBleDev() {
        viewModelScope.launch(Dispatchers.IO) {
            dataRepository.getBleDevStoreFlow().collect{
                if (it.address.isNotEmpty()) {
                    sendToBle.addressForSearch = it.address
                    bleManager.connectDevice()
                }
            }
        }
    }
    private fun startWorkOutService(training: Training) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                sendToWorkService.training = MutableStateFlow(training)
                sendToWorkService.stateRunning = MutableStateFlow(StateRunning.Started )
                sendToWorkService.enableSpeechDescription =
                    MutableStateFlow(dataRepository.getSetting(R.string.speech_description).value == 1)
                serviceManager.startWorkout(sendToWorkService)
            }.fold(
                onSuccess = { it?.let { receiveStateWorkout(it) }  },
                onFailure = { errorApp.errorApi(it.message!!) }
            )
        }
    }
    private fun receiveStateWorkout(sendToUI: SendToUI){
        viewModelScope.launch(Dispatchers.IO) {
            sendToUI.stateRunning.collect{
                sendToWorkService.stateRunning.value = it
                if (it == StateRunning.Stopped) {
                    sendToWorkService = SendToWorkService()
                }
                _playWorkoutScreenState.update { currentState ->
                    currentState.copy(switchState = it) } }
        }
        viewModelScope.launch(Dispatchers.IO) {
            sendToUI.set.collect{ set->
                _playWorkoutScreenState.update { currentState ->
                    currentState.copy( playerSet = set )}}
        }
        viewModelScope.launch(Dispatchers.IO) {
            sendToUI.nextSet.collect{ set->
                _playWorkoutScreenState.update { currentState ->
                    currentState.copy(
                        listActivity = if (set != null ) currentState.activityList(set.idSet)
                                        else currentState.listActivity)}}
        }
        viewModelScope.launch(Dispatchers.IO) {
            sendToUI.flowTick.collect { tick ->
                _playWorkoutScreenState.update { currentState ->
                    currentState.copy( tickTime = tick )}}
        }
        viewModelScope.launch(Dispatchers.IO) {
            sendToUI.durationSpeech.collect { duration ->
                dataRepository.updateDuration(duration)}
        }
        viewModelScope.launch(Dispatchers.IO) {
            sendToUI.messageList.collect { state ->
                _playWorkoutScreenState.update { currentState ->
                    currentState.copy( statesWorkout = state ) } }
        }
    }
    private fun stopWorkOutService(){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { serviceManager.stopWorkout() }.fold(
                onSuccess = {
                    _playWorkoutScreenState.update { currentState ->
                        currentState.copy(
                            startTime = 0L,
                            tickTime = TickTime(hour = "00", min = "00", sec = "00"),
                            statesWorkout = emptyList(),
                            switchState = serviceManager.stateRunningService(),
                        )
                    }
                },
                onFailure = { errorApp.errorApi(it.message!!) }
            )
        }
    }
    private fun pauseWorkOutService(){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { serviceManager.pauseWorkout() }.fold(
                onSuccess = { _playWorkoutScreenState.update { currentState ->
                    currentState.copy( switchState = serviceManager.stateRunningService(),) } },
                onFailure = { errorApp.errorApi(it.message!!) }
            )
        }
    }
}
