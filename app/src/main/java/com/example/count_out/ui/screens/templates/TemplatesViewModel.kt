package com.example.count_out.ui.screens.templates

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.count_out.data.DataRepository
import com.example.count_out.entity.ErrorApp
import com.example.count_out.entity.Workout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TemplatesViewModel @Inject constructor(
    private val errorApp: ErrorApp,
    private val dataRepository: DataRepository
): ViewModel() {
    private val _workoutScreenState = MutableStateFlow(
        TemplatesScreenState( templates = mutableStateOf(emptyList()))
    )
    val workoutScreenState: StateFlow<TemplatesScreenState> = _workoutScreenState.asStateFlow()

    fun getWorkouts(){ templateMy { dataRepository.getWorkouts() } }
    fun changeNameWorkout(workout: Workout){ templateMy { dataRepository.changeNameWorkout(workout) } }
    fun deleteWorkout(id: Long){ templateMy { dataRepository.deleteWorkout(id) } }
    fun addWorkout(name: String){ templateMy { dataRepository.addWorkout(name) } }
    private fun templateMy( funDataRepository:() -> List<Workout> ){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { funDataRepository() }.fold(
                onSuccess = { _workoutScreenState.update { currentState ->
                    currentState.copy(templates = mutableStateOf(it) ) } },
                onFailure = { errorApp.errorApi(it.message!!) }
            )
        }
    }
}