package com.example.count_out.ui.screens.play_workout

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.count_out.data.DataRepository
import com.example.count_out.entity.ErrorApp
import com.example.count_out.entity.StateWorkOut
import com.example.count_out.entity.TickTime
import com.example.count_out.entity.Training
import com.example.count_out.service.ServiceManager
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
            pauseWorkOutService = { pauseWorkOutService( ) }
        ))
    val playWorkoutScreenState: StateFlow<PlayWorkoutScreenState> = _playWorkoutScreenState.asStateFlow()

    init {
        if (serviceManager.isStarted) receiveStateWorkout()
    }
    fun getTraining(id: Long) {
        templateMy { dataRepository.getTraining(id) } }

    private fun startWorkOutService(training: Training)
    {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { serviceManager.startWorkout(training) }.fold(
                onSuccess = { receiveStateWorkout() },
                onFailure = { errorApp.errorApi(it.message!!) }
            )
        }
    }
    private fun receiveStateWorkout(){
        viewModelScope.launch(Dispatchers.IO) {
            serviceManager.flowTick.collect { tick ->
                _playWorkoutScreenState.update { currentState -> currentState.copy( tickTime = tick )}}
        }
        viewModelScope.launch(Dispatchers.IO) {
            serviceManager.flowStateService.collect { state -> collectState(state) } }
    }
    private fun collectState(stateService: StateWorkOut){
        if (stateService.state != null){
            val states = _playWorkoutScreenState.value.statesWorkout.value.toMutableList()
//            log(show, "PlayWorkoutViewModel states: $states")
            states.add(stateService)
            _playWorkoutScreenState.update { screenState ->
                screenState.copy(statesWorkout = mutableStateOf( states )) }
            if (stateService.time != null && _playWorkoutScreenState.value.startTime == 0L){
                _playWorkoutScreenState.update { screenState ->
                    screenState.copy(startTime = stateService.time) }
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
                onSuccess = {  },
                onFailure = { errorApp.errorApi(it.message!!) }
            )
        }
    }
    private fun templateMy( funDataRepository:() -> Training ){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { funDataRepository() }.fold(
                onSuccess = {  _playWorkoutScreenState.update {
                        currentState -> currentState.copy( training = it ) } },
                onFailure = { errorApp.errorApi(it.message!!) }
            )
        }
    }
}
