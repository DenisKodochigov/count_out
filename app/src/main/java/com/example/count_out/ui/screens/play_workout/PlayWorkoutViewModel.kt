package com.example.count_out.ui.screens.play_workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.count_out.data.DataRepository
import com.example.count_out.data.room.tables.StateWorkOutDB
import com.example.count_out.entity.ErrorApp
import com.example.count_out.entity.Training
import com.example.count_out.service.ServiceManager
import com.example.count_out.service.WorkoutService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

    private val _stateWorkoutService: MutableStateFlow<StateWorkOutDB> = MutableStateFlow(StateWorkOutDB())

    init {
        serviceManager.bindService(WorkoutService::class.java)
        _stateWorkoutService
            .onEach { state ->
                if (state.state != null){
                    val states = _playWorkoutScreenState.value.stateWorkout.toMutableList()
                    states.add(state)
                    _playWorkoutScreenState.update { screenState -> screenState.copy(stateWorkout = states) }
                    if (state.time != null && _playWorkoutScreenState.value.startTime == 0L){
                        _playWorkoutScreenState.update { screenState -> screenState.copy(startTime = state.time) }
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun getTraining(id: Long) {
        templateMy { dataRepository.getTraining(id) } }

    private fun startWorkOutService(training: Training){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                serviceManager.startWorkout( training, _stateWorkoutService )
            }.fold(
                onSuccess = { },
                onFailure = { errorApp.errorApi(it.message!!) }
            )
        }
    }

    private fun stopWorkOutService(){
        serviceManager.stopWorkout()
    }
    private fun pauseWorkOutService(){
        serviceManager.pauseWorkout()
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