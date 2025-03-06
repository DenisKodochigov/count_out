package com.count_out.app.presentation.screens.executor

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.count_out.app.presentation.models.DataForServImpl
import com.count_out.domain.entity.enums.RunningState
import com.count_out.app.presentation.models.DataForUIImpl
import com.count_out.app.presentation.models.TickTimeImpl
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
//    private val messageApp: MessageApp,
//    private val internet: Internet,
//    private val dataRepository: DataRepository,
//    private val serviceBind: CountOutServiceBind
): ViewModel() {
    private val _executeWorkoutScreenState = MutableStateFlow(
        ExecuteWorkoutScreenState(
//            startWorkOutService = {
//                dataForServ.training.value = it
//                commandService(CommandService.START_WORK) },
//            stopWorkOutService = { commandService(CommandService.STOP_WORK) },
//            pauseWorkOutService = { commandService(CommandService.PAUSE_WORK) },
//            saveTraining = { commandService( CommandService.SAVE_TRAINING)},
//            notSaveTraining = { commandService( CommandService.NOT_SAVE_TRAINING)},
//            updateSet = { trainingId, setDB -> updateSet(trainingId, setDB) }
        ))
    val executeWorkoutScreenState: StateFlow<ExecuteWorkoutScreenState> =
        _executeWorkoutScreenState.asStateFlow()

    private val dataForServ = DataForServImpl()

    fun getTraining(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
//            kotlin.runCatching { dataRepository.getTraining(id) }.fold(
//                onSuccess = {
//                    dataForServ.training.value = it
//                    startCountOutService()
//                    connectToStoredBleDev()
//                    _executeWorkoutScreenState.update { state -> state.copy( training = it,) } },
//                onFailure = { messageApp.errorApi(it.message ?: "") }
//            )
        }
    }
    private fun startCountOutService(){
        viewModelScope.launch(Dispatchers.IO) {
//            runCatching {
//                serviceBind.service.startCountOutService(dataForServ = dataForServ)
//            }.fold(
//                onSuccess = { receiveState( it ) },
//                onFailure = {
//                    messageApp.errorApi(
//                        id = R.string.start_service, errorMessage = " ${it.message ?: ""}")
//                }
//            )
        }
    }

//    private fun commandService(command: CommandService){
//        viewModelScope.launch(Dispatchers.IO) {
//            kotlin.runCatching { serviceBind.service.commandService(command) }.fold(
//                onSuccess = { },
//                onFailure = { messageApp.errorApi("initServiceApp ${it.message ?: ""}") }
//            )
//        }
//    }
    private fun connectToStoredBleDev() {
        viewModelScope.launch(Dispatchers.IO) {
//            dataRepository.getBleDevStoreFlow().collect{ device->
//                if (device.address.isNotEmpty()) {
//                    _executeWorkoutScreenState.update { state -> state.copy(lastConnectHearthRateDevice = device) }
//                    dataForServ.addressForSearch = device.address
//                    serviceBind.service.commandService(CommandService.CONNECT_DEVICE)
//                }
//            }
        }
    }
//    private fun updateSet(trainingId: Long, set: SetDB) {
//        viewModelScope.launch(Dispatchers.IO) {
//            kotlin.runCatching { dataRepository.updateSet(trainingId, set) }.fold(
//                onSuccess = {
//                    dataForServ.training.value = it
//                    _executeWorkoutScreenState.update { state -> state.copy( training = it ) } },
//                onFailure = { messageApp.errorApi(it.message ?: "") }
//            )
//        }
//        dataForServ.interval.value = set.intervalReps
//        dataForServ.idSetChangeInterval.value = set.idSet
//    }

    private fun receiveState(dataForUI: DataForUIImpl){
        viewModelScope.launch(Dispatchers.IO) {}
//            dataForUI.durationSpeech.collect { duration ->dataRepository.updateDuration(duration)} } //save duration set time
        viewModelScope.launch(Dispatchers.IO) {
            dataForUI.coordinate.collect{ loc->
                _executeWorkoutScreenState.update { state -> state.copy( coordinate = loc )}} } //coordinate
        viewModelScope.launch(Dispatchers.IO) {
            dataForUI.runningState.collect{ runningState->
                runningState?.let { runState->
                    _executeWorkoutScreenState.update { state -> state.copy(stateWorkOutService = runState) }
                    if (runState == RunningState.Stopped) {
                        dataForServ.empty()
                        _executeWorkoutScreenState.update { state ->
                            state.copy(
                                startTime = 0L,
                                flowTime = TickTimeImpl(hour = "00", min = "00", sec = "00"),
                                showBottomSheetSaveTraining = mutableStateOf(true)
                            )
                        }
                        return@collect
                    }
                }
            } } //stateWorkOutService
        viewModelScope.launch(Dispatchers.IO) {
            dataForUI.flowTime.collect { tick ->
                _executeWorkoutScreenState.update { state ->
                    state.copy(
                    flowTime = tick as TickTimeImpl,
                    currentRest = dataForUI.countRest.value,
                    enableChangeInterval = dataForUI.enableChangeInterval.value,
                )}
            }
        } //tickTime
        viewModelScope.launch(Dispatchers.IO) {
            dataForUI.stepTraining.collect {
                _executeWorkoutScreenState.update { state -> state.copy(stepTraining = it) } } } //stepInfo
        viewModelScope.launch(Dispatchers.IO) {
            dataForUI.currentCount.collect { count ->
                _executeWorkoutScreenState.update { state -> state.copy(currentCount = count) } } } //currentCount
        viewModelScope.launch(Dispatchers.IO) {
            dataForUI.currentDistance.collect { count ->
                _executeWorkoutScreenState.update { state -> state.copy(currentDistance = count) } } } //currentDistance
        viewModelScope.launch(Dispatchers.IO) {
            dataForUI.currentDuration.collect { count ->
                _executeWorkoutScreenState.update { state -> state.copy(currentDuration = count) } } } //currentDuration
        viewModelScope.launch(Dispatchers.IO) {
            dataForUI.bleConnectState.collect { stateC ->
                _executeWorkoutScreenState.update { state -> state.copy( bleConnectState = stateC) } } } //connectingState
        viewModelScope.launch(Dispatchers.IO) {
            dataForUI.heartRate.collect { hr ->
                _executeWorkoutScreenState.update { state -> state.copy(heartRate = hr) } } } //heartRate
    }
//    fun availableInternet() = internet.isOnline()
}
