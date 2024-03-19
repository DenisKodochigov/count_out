package com.example.count_out.ui.screens.play_workout

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.count_out.data.DataRepository
import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.entity.ErrorApp
import com.example.count_out.entity.StateRunning
import com.example.count_out.entity.TickTime
import com.example.count_out.entity.Training
import com.example.count_out.entity.VariablesInService
import com.example.count_out.entity.VariablesOutService
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
    private val serviceManager: ServiceManager,
): ViewModel() {
    private val _playWorkoutScreenState = MutableStateFlow(
        PlayWorkoutScreenState(
            startWorkOutService = { startWorkOutService( it ) },
            stopWorkOutService = { stopWorkOutService( ) },
            pauseWorkOutService = { pauseWorkOutService( ) },
            updateSet = { trainingId, setDB -> updateSet ( trainingId, setDB) }
        ))
    val playWorkoutScreenState: StateFlow<PlayWorkoutScreenState> = _playWorkoutScreenState.asStateFlow()

    val variablesInService = VariablesInService()
    init {
        val continues = serviceManager.stateRunningService() == StateRunning.Started ||
                serviceManager.stateRunningService() == StateRunning.Pause
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                if (continues) serviceManager.connectingToService(variablesInService)
                else VariablesOutService()
            }.fold(
                onSuccess = { if (continues) receiveStateWorkout(it) },
                onFailure = { errorApp.errorApi(it.message!!) }
            )
        }
    }
    fun getTraining(id: Long) {
        templateMy { dataRepository.getTraining(id) } }
    private fun updateSet(trainingId: Long,set: SetDB) {
        templateMy { dataRepository.updateSet( trainingId, set) }
    }
    private fun startWorkOutService(training: Training)
    {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                variablesInService.training = MutableStateFlow(training)
                variablesInService.stateRunning = MutableStateFlow(StateRunning.Started)
                serviceManager.startWorkout( variablesInService )
            }.fold(
                onSuccess = { receiveStateWorkout(it) },
                onFailure = { errorApp.errorApi(it.message!!) }
            )
        }
    }
    private fun receiveStateWorkout(variablesOut: VariablesOutService){
        viewModelScope.launch(Dispatchers.IO) {
            variablesOut.stateRunning.collect{
                _playWorkoutScreenState.update { screenState ->
                    screenState.copy(switchState = mutableStateOf(it))
                }
                if (it == StateRunning.Stopped) stopWorkOutService()
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            variablesOut.set?.let {
                it.collect{ set->
                    _playWorkoutScreenState.update { currentState ->
                        currentState.copy( playerSet = mutableStateOf(set) )}
                }
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            variablesOut.flowTick.collect { tick ->
                _playWorkoutScreenState.update { currentState -> currentState.copy( tickTime = tick )}}
        }
        viewModelScope.launch(Dispatchers.IO) {
            variablesOut.messageList.collect { state ->
                _playWorkoutScreenState.update { screenState ->
                    screenState.copy( statesWorkout = mutableStateOf( state )) }
                if (state.isNotEmpty()) {
                    if (state.last().time != null && _playWorkoutScreenState.value.startTime == 0L){
                        _playWorkoutScreenState.update { screenState ->
                            screenState.copy(startTime = state.last().time!!) }
                    }
                }
            }
        }
    }

    private fun stopWorkOutService(){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { serviceManager.stopWorkout() }.fold(
                onSuccess = {
                    _playWorkoutScreenState.update { screenState ->
                        screenState.copy(
                            startTime = 0L,
                            tickTime = TickTime(hour = "00", min = "00", sec = "00"),
                            statesWorkout = mutableStateOf(emptyList()),
                            switchState = mutableStateOf(serviceManager.stateRunningService()),
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
                onSuccess = { _playWorkoutScreenState.update { screenState ->
                    screenState.copy( switchState = mutableStateOf(serviceManager.stateRunningService()),) } },
                onFailure = { errorApp.errorApi(it.message!!) }
            )
        }
    }
    private fun templateMy( funDataRepository:() -> Training ){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { funDataRepository() }.fold(
                onSuccess = {
                    _playWorkoutScreenState.update {
                            currentState -> currentState.copy( training = it ) }
                    variablesInService.training.value = it
                },
                onFailure = { errorApp.errorApi(it.message!!) }
            )
        }
    }
}
//@HiltViewModel
//class PlayWorkoutViewModel @Inject constructor(
//    private val errorApp: ErrorApp,
//    private val dataRepository: DataRepository,
//    private val serviceManager: ServiceManager,
//): ViewModel() {
//    private val _playWorkoutScreenState = MutableStateFlow(
//        PlayWorkoutScreenState(
//            startWorkOutService = { startWorkOutService( it ) },
//            stopWorkOutService = { stopWorkOutService( ) },
//            pauseWorkOutService = { pauseWorkOutService( ) },
//            updateSet = { trainingId, setDB -> updateSet ( trainingId, setDB) }
//        ))
//    val playWorkoutScreenState: StateFlow<PlayWorkoutScreenState> = _playWorkoutScreenState.asStateFlow()
//
//    init {
//        if (serviceManager.isStarted == StateRunning.Started) receiveStateWorkout()
//        _playWorkoutScreenState.update { currentState ->
//            currentState.copy ( switchState = mutableStateOf(StateRunning.Stoped))}
//    }
//    fun getTraining(id: Long) {
//        templateMy { dataRepository.getTraining(id) } }
//    private fun updateSet(trainingId: Long,set: SetDB) {
//        templateMy { dataRepository.updateSet( trainingId, set) }
//    }
//
//
//    private fun startWorkOutService(training: Training)
//    {
//        viewModelScope.launch(Dispatchers.IO) {
//            kotlin.runCatching {
////=======================================================================
//                serviceManager.variablesIn = VariablesInService(
//                    training = MutableStateFlow(training),
//                    stateRunning = MutableStateFlow(StateRunning.Started)
//                )
////=======================================================================
//                if (serviceManager.isStarted == StateRunning.Pause) {
//                    serviceManager.continuationService()
//                } else {
//                    sendTrainingFlow(training)
//                    serviceManager.startWorkout()
//                }
//            }.fold(
//                onSuccess = { receiveStateWorkout() },
//                onFailure = { errorApp.errorApi(it.message!!) }
//            )
//        }
//    }
//    private fun receiveStateWorkout(){
//        viewModelScope.launch(Dispatchers.IO) {
//            _playWorkoutScreenState.update { screenState ->
//                screenState.copy(switchState = mutableStateOf(serviceManager.isStarted))
//            }
//        }
//        viewModelScope.launch(Dispatchers.IO) {
//            serviceManager.flowTick.collect { tick ->
//                _playWorkoutScreenState.update { currentState -> currentState.copy( tickTime = tick )}}
//        }
//        viewModelScope.launch(Dispatchers.IO) {
//            serviceManager.flowListOut.collect { state ->
//                _playWorkoutScreenState.update { screenState ->
//                    screenState.copy( statesWorkout = mutableStateOf( state )) }
//                if (state.isNotEmpty()) {
//                    if (state.last().time != null && _playWorkoutScreenState.value.startTime == 0L){
//                        _playWorkoutScreenState.update { screenState ->
//                            screenState.copy(startTime = state.last().time!!) }
//                    }
//                }
//            }
//        }
//    }
//
//    private fun stopWorkOutService(){
//        viewModelScope.launch(Dispatchers.IO) {
//            kotlin.runCatching { serviceManager.stopWorkout() }.fold(
//                onSuccess = {
//                    _playWorkoutScreenState.update { screenState ->
//                        screenState.copy(
//                            startTime = 0L,
//                            tickTime = TickTime(hour = "00", min = "00", sec = "00"),
//                            statesWorkout = mutableStateOf(emptyList()),
//                            switchState = mutableStateOf(serviceManager.isStarted),
//                        )
//                    }
//                },
//                onFailure = { errorApp.errorApi(it.message!!) }
//            )
//        }
//    }
//    private fun pauseWorkOutService(){
//        viewModelScope.launch(Dispatchers.IO) {
//            kotlin.runCatching { serviceManager.pauseWorkout() }.fold(
//                onSuccess = { _playWorkoutScreenState.update { screenState ->
//                    screenState.copy( switchState = mutableStateOf(serviceManager.isStarted),) } },
//                onFailure = { errorApp.errorApi(it.message!!) }
//            )
//        }
//    }
//    private fun templateMy( funDataRepository:() -> Training ){
//        viewModelScope.launch(Dispatchers.IO) {
//            kotlin.runCatching { funDataRepository() }.fold(
//                onSuccess = {
//                    _playWorkoutScreenState.update {
//                        currentState -> currentState.copy( training = it ) }
//                    sendTrainingFlow(it)
//                },
//                onFailure = { errorApp.errorApi(it.message!!) }
//            )
//        }
//    }
//
//    private fun sendTrainingFlow(training: Training){
//        serviceManager.flowTraining.value = training
//    }
//}
