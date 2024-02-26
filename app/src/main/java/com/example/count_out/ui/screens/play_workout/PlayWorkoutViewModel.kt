package com.example.count_out.ui.screens.play_workout

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.count_out.data.DataRepository
import com.example.count_out.entity.ErrorApp
import com.example.count_out.entity.StateWorkOut
import com.example.count_out.entity.Training
import com.example.count_out.service.ServiceManager
import com.example.count_out.service.WorkoutService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.annotation.Signed
import javax.inject.Inject

@Signed
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
        serviceManager.bindService(WorkoutService::class.java)
    }

    private fun collectState(stateService: StateWorkOut){
        if (stateService.state != null){
            val states = _playWorkoutScreenState.value.statesWorkout.value.toMutableList()
            states.add(stateService)
            _playWorkoutScreenState.update { screenState ->
                screenState.copy(statesWorkout = mutableStateOf( states )) }
            if (stateService.time != null && _playWorkoutScreenState.value.startTime == 0L){
                _playWorkoutScreenState.update { screenState ->
                    screenState.copy(startTime = stateService.time!!) }
            }
        }
    }

    fun getTraining(id: Long) {
        templateMy { dataRepository.getTraining(id) } }

    private fun startWorkOutService(training: Training){
        serviceManager.startWorkout( training) { collectState(it) }
    }

    private fun stopWorkOutService(){
        _playWorkoutScreenState.update { screenState -> screenState.copy(startTime = 0L) }
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
