package com.example.count_out.ui.screens.exercise

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.count_out.data.DataRepository
import com.example.count_out.data.room.tables.ExerciseDB
import com.example.count_out.entity.ErrorApp
import com.example.count_out.entity.Exercise
import com.example.count_out.entity.Set
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val errorApp: ErrorApp,
    private val dataRepository: DataRepository
): ViewModel() {

    private val _exerciseScreenState = MutableStateFlow(
        ExerciseScreenState(
            nameTraining = "",
            nameRound = "",
            roundId = 0,
            exercise = ExerciseDB(),
            onAddUpdateSet = { idExercise, set -> addUpdateSet(idExercise, set) },
            onSelectActivity = {
                exerciseId, activityId -> setActivityToExercise(exerciseId, activityId) },
            onSetColorActivity = {
                    activityId, color -> onSetColorActivity(activityId = activityId, color = color) },
            triggerRunOnClickFAB = mutableStateOf(false),
            listSpeech = emptyList(),
            nameSection  = "",
            item = null,
        ))
    val exerciseScreenState: StateFlow<ExerciseScreenState> = _exerciseScreenState.asStateFlow()

    private fun addUpdateSet(exerciseId:Long, set: Set){
        templateMy { dataRepository.addUpdateSet( exerciseId, set) } }

    private fun setActivityToExercise(exerciseId: Long, activityId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { dataRepository.setActivityToExercise(exerciseId, activityId) }
                .fold( onSuccess = {
                        _exerciseScreenState.update { currentState ->
                            currentState.copy(
                                exercise = it,
                                showBottomSheetSelectActivity = mutableStateOf(false)) }
                    },
                    onFailure = { errorApp.errorApi(it.message!!) }
                )
        }
    }
    private fun onSetColorActivity(activityId: Long, color: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { dataRepository.onSetColorActivity( activityId, color) }
                .fold( onSuccess = { }, onFailure = { errorApp.errorApi(it.message!!) })
        }
    }
    fun getExercise(roundId: Long, exerciseId:Long) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { dataRepository.getExercise(roundId, exerciseId) }.fold(
                onSuccess = {
                    _exerciseScreenState.update { currentState -> currentState.copy(exercise = it) } },
                onFailure = { errorApp.errorApi(it.message!!) }
            )
            kotlin.runCatching { dataRepository.getNameTraining(roundId) }.fold(
                onSuccess = {
                    _exerciseScreenState.update { currentState -> currentState.copy(nameTraining = it) } },
                onFailure = { errorApp.errorApi(it.message!!) }
            )
            kotlin.runCatching { dataRepository.getNameRound(roundId) }.fold(
                onSuccess = {
                    _exerciseScreenState.update { currentState -> currentState.copy(nameRound = it) } },
                onFailure = { errorApp.errorApi(it.message!!) }
            )
            kotlin.runCatching { dataRepository.getActivities() }.fold(
                onSuccess = {
                    _exerciseScreenState.update { currentState -> currentState.copy(activities = it) } },
                onFailure = { errorApp.errorApi(it.message!!) }
            )
        }
    }

    private fun templateMy( funDataRepository:() -> Exercise ){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { funDataRepository() }.fold(
                onSuccess = {  _exerciseScreenState.update {
                        currentState -> currentState.copy( exercise = it ) } },
                onFailure = { errorApp.errorApi(it.message!!) }
            )
        }
    }
}

