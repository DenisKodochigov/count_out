package com.example.count_out.ui.screens.play_workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.count_out.data.DataRepository
import com.example.count_out.data.room.tables.StateWorkOutDB
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

    private var _stateWorkoutService: MutableStateFlow<StateWorkOut> = MutableStateFlow(StateWorkOutDB())
    val stateWorkoutService: StateFlow<StateWorkOut> = _stateWorkoutService.asStateFlow()
    init {
        serviceManager.bindService(WorkoutService::class.java)
    }
    fun getTraining(id: Long) {
        templateMy { dataRepository.getTraining(id) } }

    private fun startWorkOutService(training: Training){
        _stateWorkoutService = serviceManager.startWorkout(training)
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