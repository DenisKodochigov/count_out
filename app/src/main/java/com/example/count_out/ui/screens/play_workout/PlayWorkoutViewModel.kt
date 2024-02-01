package com.example.count_out.ui.screens.play_workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.count_out.data.DataRepository
import com.example.count_out.entity.ErrorApp
import com.example.count_out.entity.Training
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
    private val dataRepository: DataRepository
): ViewModel() {
    private val _playWorkoutScreenState = MutableStateFlow( PlayWorkoutScreenState( ))
    val playWorkoutScreenState: StateFlow<PlayWorkoutScreenState> = _playWorkoutScreenState.asStateFlow()

    fun getTraining(id: Long) { templateMy { dataRepository.getTraining(id) } }
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