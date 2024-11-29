package com.example.count_out.ui.screens.executor

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.count_out.R
import com.example.count_out.data.DataRepository
import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.entity.CommandService
import com.example.count_out.entity.Internet
import com.example.count_out.entity.MessageApp
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.TickTime
import com.example.count_out.entity.ui.DataForServ
import com.example.count_out.entity.ui.DataForUI
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
    private val internet: Internet,
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
            saveTraining = { commandService( CommandService.SAVE_TRAINING)},
            notSaveTraining = { commandService( CommandService.NOT_SAVE_TRAINING)},
            updateSet = { trainingId, setDB -> updateSet(trainingId, setDB) }
        ))
    val executeWorkoutScreenState: StateFlow<ExecuteWorkoutScreenState> =
        _executeWorkoutScreenState.asStateFlow()

    private val dataForServ = DataForServ()

    init {
        startServiceApp()
    }
    private fun startServiceApp(){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                serviceBind.service.startCountOutService(
                    dataForServ = dataForServ,
                    callBack = { connectToStoredBleDev() })
            }.fold(
                onSuccess = { receiveState( it ) },
                onFailure = { messageApp.errorApi(
                    id = R.string.start_service, errorMessage = " ${it.message ?: ""}") }
            )
        }
    }
    private fun commandSrv(command: CommandService){
        serviceBind.service.commandService( command)
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
                    lg("getTraining")
                    _executeWorkoutScreenState.update { state -> state.copy(
                        training = it,
                        speakingSet = state.getBeginningSet(it),
                        executeSetInfo = state.getExecuteSetInfo(it, 0L)
                    ) }
                },
                onFailure = { messageApp.errorApi(it.message ?: "") }
            )
        }
    }

    private fun updateSet(trainingId: Long, set: SetDB) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { dataRepository.updateSet(trainingId, set) }.fold(
                onSuccess = {
                    _executeWorkoutScreenState.update { state ->
                        state.copy(training = it, speakingSet = set) }
                    dataForServ.training.value = it
                },
                onFailure = { messageApp.errorApi(it.message ?: "") }
            )
        }
    }

    private fun connectToStoredBleDev() {
        viewModelScope.launch(Dispatchers.IO) {
            dataRepository.getBleDevStoreFlow().collect{ device->
                if (device.address.isNotEmpty()) { _executeWorkoutScreenState.update { state ->
                        state.copy(lastConnectHearthRateDevice = device) } 
                    dataForServ.addressForSearch = device.address
                    commandSrv( CommandService.CONNECT_DEVICE)
                }
            }
        }
    }

    private fun receiveState(dataForUI: DataForUI){
        viewModelScope.launch(Dispatchers.IO) {
            dataForUI.runningState.collect{
                lg("receiveState 1")
                _executeWorkoutScreenState.update { state -> state.copy(stateWorkOutService = it) }
                if (it == RunningState.Stopped) {
                    dataForServ.empty()
                    _executeWorkoutScreenState.update { state ->
                        state.copy(
                            startTime = 0L,
                            tickTime = TickTime(hour = "00", min = "00", sec = "00"),
                            speakingSet = null,
                            messageWorkout = emptyList(),
                            listActivity = emptyList(),
                            executeSetInfo = state.training?.let { state.getExecuteSetInfo(it, 0L) },
                            showBottomSheetSaveTraining = mutableStateOf(true)
                        )
                    }
                    return@collect
                }
            } } //stateWorkOutService
        viewModelScope.launch(Dispatchers.IO) {
            dataForUI.coordinate.collect{ loc->
                _executeWorkoutScreenState.update { state -> state.copy( coordinate = loc )}} } //coordinate
        viewModelScope.launch(Dispatchers.IO) {
            dataForUI.speakingSet.collect{ setCollect->
                setCollect?.let { set->
                    _executeWorkoutScreenState.update { state ->
                        state.copy( speakingSet = set,
                                    executeSetInfo = state.training?.let { train->
                                        state.getExecuteSetInfo(train, set.idSet)} )}}}} //speakingSet
//        viewModelScope.launch(Dispatchers.IO) {
//            dataForUI.nextSet.collect{ nextSet->
//                _executeWorkoutScreenState.update { state ->
//                    state.copy(listActivity = if (nextSet != null ) state.activityList(nextSet.idSet)
//                                                else state.listActivity)}} } //listActivity
        viewModelScope.launch(Dispatchers.IO) {
            dataForUI.flowTick.collect { tick ->
                _executeWorkoutScreenState.update { state -> state.copy( tickTime = tick )}} } //tickTime
        viewModelScope.launch(Dispatchers.IO) {
            dataForUI.durationSpeech.collect { duration ->dataRepository.updateDuration(duration)} } //save duration set time
        viewModelScope.launch(Dispatchers.IO) {
            dataForUI.message.collect { message ->
                message?.let { mes -> _executeWorkoutScreenState.update { state ->
                        state.copy(messageWorkout = state.addMessage(mes)) } } } } //messageWorkout
        viewModelScope.launch(Dispatchers.IO) {
            dataForUI.connectingState.collect { stateC ->
                _executeWorkoutScreenState.update { state -> state.copy( connectingState = stateC) } } } //connectingState
        viewModelScope.launch(Dispatchers.IO) {
            dataForUI.heartRate.collect { hr ->
                _executeWorkoutScreenState.update { state -> state.copy(heartRate = hr) } } } //heartRate
    }
    fun availableInternet() = internet.isOnline()
}
