package com.example.count_out.ui.screens.play_workout

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.count_out.data.DataRepository
import com.example.count_out.entity.ErrorApp
import com.example.count_out.entity.Training
import com.example.count_out.service.WorkoutService
import com.example.count_out.ui.joint.NotificationApp
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
    private val notificationApp: NotificationApp,
    private val workOutService: WorkoutService,
): ViewModel() {
    private val _playWorkoutScreenState = MutableStateFlow(
        PlayWorkoutScreenState(
            notificationApp = notificationApp,
        ))
    val playWorkoutScreenState: StateFlow<PlayWorkoutScreenState> = _playWorkoutScreenState.asStateFlow()

    fun getTraining(id: Long) { templateMy { dataRepository.getTraining(id) } }

    fun startWorkOutService(context: Context){
        workOutService.startService(context)
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