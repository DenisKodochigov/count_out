package com.example.count_out.ui.screens.settings

import androidx.lifecycle.ViewModel
import com.example.count_out.data.DataRepository
import com.example.count_out.entity.ErrorApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val errorApp: ErrorApp,
    private val dataRepository: DataRepository
): ViewModel() {
    private val _settingScreenState = MutableStateFlow(SettingScreenState())
    val settingScreenState: StateFlow<SettingScreenState> = _settingScreenState.asStateFlow()

    fun init(){}
//    fun getWorkouts(){ templateMy { dataRepository.getWorkouts() } }
//    fun changeNameWorkout(workout: Workout){ templateMy { dataRepository.changeNameWorkout(workout) } }
//    fun deleteWorkout(id: Long){ templateMy { dataRepository.deleteWorkout(id) } }
//    fun addWorkout(name: String){ templateMy { dataRepository.addWorkout(name) } }
//    private fun templateMy( funDataRepository:() -> List<Workout> ){
//        viewModelScope.launch(Dispatchers.IO) {
//            kotlin.runCatching { funDataRepository() }.fold(
//                onSuccess = { _settingScreenState.update { currentState ->
//                    currentState.copy(workouts = mutableStateOf(it) ) } },
//                onFailure = { errorApp.errorApi(it.message!!) }
//            )
//        }
//    }
}