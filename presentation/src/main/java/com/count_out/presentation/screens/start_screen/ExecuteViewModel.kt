package com.count_out.presentation.screens.start_screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.count_out.domain.entity.router.DataForUI
import com.count_out.domain.entity.enums.RunningState
import com.count_out.domain.entity.workout.ShowBottomSheet
import com.count_out.domain.use_case.execute.DownIntervalUC
import com.count_out.domain.use_case.execute.PauseWorkoutUC
import com.count_out.domain.use_case.execute.SaveWorkoutUC
import com.count_out.domain.use_case.execute.StartWorkoutUC
import com.count_out.domain.use_case.execute.StopWorkoutUC
import com.count_out.domain.use_case.execute.UpIntervalUC
import com.count_out.domain.use_case.other.ShowBottomSheetUC
import com.count_out.domain.use_case.plans.CopyTrainingUC
import com.count_out.domain.use_case.plans.GetPlanUC
import com.count_out.domain.use_case.plans.GetTrainingUC
import com.count_out.domain.use_case.plans.GetTrainingsUC
import com.count_out.presentation.R
import com.count_out.presentation.models.DataForServImpl
import com.count_out.presentation.models.Internet
import com.count_out.presentation.models.MessageApp
import com.count_out.presentation.models.TickTimeImplP
import com.count_out.presentation.models.TrainingImplP
import com.count_out.presentation.screens.plans.PlansConvertor
import com.count_out.presentation.screens.plans.PlansEvent
import com.count_out.presentation.screens.plans.PlansState
import com.count_out.presentation.screens.prime.Event
import com.count_out.presentation.screens.prime.PrimeViewModel
import com.count_out.presentation.screens.prime.ScreenState
import com.count_out.presentation.screens.training.TrainingEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExecuteViewModel @Inject constructor(
    private val startWorkoutUC: StartWorkoutUC,
    private val stopWorkoutUC: StopWorkoutUC,
    private val pauseWorkoutUC: PauseWorkoutUC,
    private val saveWorkoutUC: SaveWorkoutUC,
    private val upIntervalUC: UpIntervalUC,
    private val downIntervalUC: DownIntervalUC,
    private val getPlanUC: GetPlanUC,
    private val showBottomSheetUC: ShowBottomSheetUC,
    private val internet: Internet,
): PrimeViewModel<ExecuteState, ExecuteConverter>() {

    override fun initScreenState(): ScreenState<ExecuteState> = ScreenState.Loading
    override fun initDataState(): ExecuteState = ExecuteState()
    override fun initConvertor(): ExecuteConverter = ExecuteConverter()

    override fun routeEvent(event: Event) {
        when (event) {
            is ExecuteEvent.BackScreen -> { navigate.backStack()}
            is ExecuteEvent.SelectPlan -> { navigate.goToScreenPlans()}
            is ExecuteEvent.Start -> { startWorkOut() }
            is ExecuteEvent.Stop -> { stopWorkOut() }
            is ExecuteEvent.Pause -> { pauseWorkOut() }
            is ExecuteEvent.Save -> { saveWorkOut() }
            is ExecuteEvent.UpInterval -> { upInterval() }
            is ExecuteEvent.DownInterval -> { downInterval() }
            is ExecuteEvent.GetPlan -> { getPlan() }
            is ExecuteEvent.ShowBS -> { showBottomSheet(event.item) }
        }
    }

    private val dataForServ = DataForServImpl()

    private fun getPlan(){
        viewModelScope.launch(Dispatchers.IO) {
            getPlanUC.execute(GetPlanUC.Request).collect {
                Log.d("KDS", "ExecuteViewModel.getPlan 1 ${it} ")
                submitState( it ) }
        }
    }
    private fun startWorkOut(){
        viewModelScope.launch(Dispatchers.IO) {
            startWorkoutUC.execute(StartWorkoutUC.Request).collect { submitState( it ) }
        }
    }
    private fun stopWorkOut(){
        viewModelScope.launch(Dispatchers.IO) {
            stopWorkoutUC.execute(StopWorkoutUC.Request).collect { submitState( it ) }
        }
    }
    private fun pauseWorkOut(){
        viewModelScope.launch(Dispatchers.IO) {
            pauseWorkoutUC.execute(PauseWorkoutUC.Request).collect { submitState( it ) }
        }
    }
    private fun saveWorkOut(){
        viewModelScope.launch(Dispatchers.IO) {
            saveWorkoutUC.execute(SaveWorkoutUC.Request).collect { submitState( it ) }
        }
    }
    private fun upInterval(){
        viewModelScope.launch(Dispatchers.IO) {
            upIntervalUC.execute(UpIntervalUC.Request).collect { submitState( it ) }
        }
    }
    private fun downInterval(){
        viewModelScope.launch(Dispatchers.IO) {
            downIntervalUC.execute(DownIntervalUC.Request).collect { submitState( it ) }
        }
    }
    private fun showBottomSheet(item: ShowBottomSheet){
        viewModelScope.launch(Dispatchers.IO) {
            showBottomSheetUC.execute( ShowBottomSheetUC.Request(item)).collect { submitState( it ) }
        }
    }
//
//    fun getTraining(id: Long) {
//        viewModelScope.launch(Dispatchers.IO) {
//            kotlin.runCatching {
////                dataRepository.getTraining(id)
//            }.fold(
//                onSuccess = {
////                    dataForServ.training.value = it
//                    startCountOutService()
//                    connectToStoredBleDev()
////                    _executeWorkoutScreenState.update { state -> state.copy( training = it,) }
//                            },
//                onFailure = { messageApp.errorApi(it.message ?: "") }
//            )
//        }
//    }
//    private fun startCountOutService(){
//        viewModelScope.launch(Dispatchers.IO) {
//            kotlin.runCatching {
////                serviceBind.service.startCountOutService(dataForServ = dataForServ as DataForServ)
//            }.fold(
//                onSuccess = {
////                    receiveState( it )
//                            },
//                onFailure = { messageApp.errorApi(
//                    id = R.string.start_service, errorMessage = " ${it.message ?: ""}") }
//            )
//        }
//    }
//
////    private fun commandService(command: CommandService){
////        viewModelScope.launch(Dispatchers.IO) {
////            kotlin.runCatching { serviceBind.service.commandService(command) }.fold(
////                onSuccess = { },
////                onFailure = { messageApp.errorApi("initServiceApp ${it.message ?: ""}") }
////            )
////        }
////    }
//    private fun connectToStoredBleDev() {
//        viewModelScope.launch(Dispatchers.IO) {
////            dataRepository.getBleDevStoreFlow().collect{ device->
////                if (device.address.isNotEmpty()) {
////                    _executeWorkoutScreenState.update { state -> state.copy(lastConnectHearthRateDevice = device) }
////                    dataForServ.addressForSearch = device.address
////                    serviceBind.service.commandService(CommandService.CONNECT_DEVICE)
////                }
////            }
//        }
//    }
//    private fun updateSet(trainingId: Long, ) { //set: SetDB) {
//        viewModelScope.launch(Dispatchers.IO) {
//            kotlin.runCatching {
////                dataRepository.updateSet(trainingId, set)
//            }.fold(
//                onSuccess = {
////                    dataForServ.training.value = it
////                    _executeWorkoutScreenState.update { state -> state.copy( training = it ) }
//                            },
//                onFailure = { messageApp.errorApi(it.message ?: "") }
//            )
//        }
////        dataForServ.interval.value = set.intervalReps
////        dataForServ.idSetChangeInterval.value = set.idSet
//    }
//
//    private fun receiveState(dataForUI: DataForUI){
////        viewModelScope.launch(Dispatchers.IO) {
////            dataForUI.durationSpeech.collect { duration ->dataRepository.updateDuration(duration)} } //save duration set time
//        viewModelScope.launch(Dispatchers.IO) {
//            dataForUI.coordinate.collect{ loc->
//                _executeState.update { state -> state.copy( coordinate = loc )}} } //coordinate
//        viewModelScope.launch(Dispatchers.IO) {
//            dataForUI.runningState.collect{ runningState->
//                runningState?.let { runState->
//                    _executeState.update { state -> state.copy(stateWorkOutService = runState) }
//                    if (runState == RunningState.Stopped) {
//                        dataForServ.empty()
//                        _executeState.update { state ->
//                            state.copy(
//                                startTime = 0L,
//                                flowTime = TickTimeImplP(hour = "00", min = "00", sec = "00"),
//                                showBottomSheetSaveTraining = mutableStateOf(true)
//                            )
//                        }
//                        return@collect
//                    }
//                }
//            } } //stateWorkOutService
//        viewModelScope.launch(Dispatchers.IO) {
//            dataForUI.flowTime.collect { tick ->
//                _executeState.update { state ->
//                    state.copy(
//                    flowTime = tick ?: TickTimeImplP("00","00","00"),
//                    currentRest = dataForUI.countRest.value,
//                    enableChangeInterval = dataForUI.enableChangeInterval.value,
//                )}
//            }
//        } //tickTime
//        viewModelScope.launch(Dispatchers.IO) {
//            dataForUI.stepTraining.collect {
//                _executeState.update { state -> state.copy(stepTraining = it) } } } //stepInfo
//        viewModelScope.launch(Dispatchers.IO) {
//            dataForUI.currentCount.collect { count ->
//                _executeState.update { state -> state.copy(currentCount = count) } } } //currentCount
//        viewModelScope.launch(Dispatchers.IO) {
//            dataForUI.currentDistance.collect { count ->
//                _executeState.update { state -> state.copy(currentDistance = count) } } } //currentDistance
//        viewModelScope.launch(Dispatchers.IO) {
//            dataForUI.currentDuration.collect { count ->
//                _executeState.update { state -> state.copy(currentDuration = count) } } } //currentDuration
//        viewModelScope.launch(Dispatchers.IO) {
//            dataForUI.bleConnectState.collect { stateC ->
//                _executeState.update { state -> state.copy( bleConnectState = stateC) } } } //connectingState
//        viewModelScope.launch(Dispatchers.IO) {
//            dataForUI.heartRate.collect { hr ->
//                _executeState.update { state -> state.copy(heartRate = hr) } } } //heartRate
//    }
//    fun availableInternet() = internet.isOnline()
}
