package com.example.count_out.ui.screens.action

import androidx.lifecycle.ViewModel
import com.example.count_out.data.DataRepository
import com.example.count_out.entity.MessageApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ActionViewModel @Inject constructor(
    private val messageApp: MessageApp,
    private val dataRepository: DataRepository
): ViewModel() {
    private val _actionScreenState = MutableStateFlow(ActionScreenState())
    val actionScreenState: StateFlow<ActionScreenState> = _actionScreenState.asStateFlow()

//    fun getWorkouts(){ templateMy { dataRepository.getWorkouts() } }
//    fun changeNameWorkout(workout: Workout){ templateMy { dataRepository.changeNameWorkout(workout) } }
//    fun deleteWorkout(id: Long){ templateMy { dataRepository.deleteWorkout(id) } }
//    fun addWorkout(name: String){ templateMy { dataRepository.addWorkout(name) } }
//    private fun templateMy( funDataRepository:() -> List<Workout> ){
//        viewModelScope.launch(Dispatchers.IO) {
//            kotlin.runCatching { funDataRepository() }.fold(
//                onSuccess = { _roundScreenState.update { currentState ->
//                    currentState.copy(workouts = mutableStateOf(it) ) } },
//                onFailure = { errorApp.errorApi(it.message!!) }
//            )
//        }
//    }
}