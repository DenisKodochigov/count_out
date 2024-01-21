package com.example.count_out.ui.screens.training

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.count_out.data.DataRepository
import com.example.count_out.data.room.tables.TrainingDB
import com.example.count_out.entity.ErrorApp
import com.example.count_out.entity.Speech
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
class TrainingViewModel @Inject constructor(
    private val errorApp: ErrorApp,
    private val dataRepository: DataRepository
): ViewModel()
{
    private val _trainingScreenState = MutableStateFlow(
        TrainingScreenState(
            training = TrainingDB(),
            enteredName = mutableStateOf(""),
            changeNameTraining = { training, name -> changeNameTraining(training, name) },
            onDeleteTraining = { trainingId -> deleteTraining(trainingId) },
            onConfirmationSpeech = { speech, item -> setSpeech( speech, item ) },
            onSpeechTraining = {},
            onSpeechRound = {},
            onDismissSpeechTrainingBS = {},
            onDismissSpeechExerciseBS = {},
            onSpeechExercise = {},
            onAddExercise = { roundId -> addExercise( roundId )},
            onCopyExercise = { trainingId, exerciseId -> copyExercise(trainingId, exerciseId)},
            onDeleteExercise = { trainingId, exerciseId -> deleteExercise(trainingId, exerciseId)},onSelectActivity = {
                    exerciseId, activityId -> setActivityToExercise(exerciseId, activityId) },
            onSetColorActivity = {
                    activityId, color -> onSetColorActivity(activityId = activityId, color = color) },
            onSave = {},
            onClickWorkout = {},
        )
    )
    val trainingScreenState: StateFlow<TrainingScreenState> = _trainingScreenState.asStateFlow()

    fun getTraining(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { dataRepository.getActivities() }.fold(
                onSuccess = {
                    _trainingScreenState.update { currentState -> currentState.copy(activities = it) } },
                onFailure = { errorApp.errorApi(it.message!!) }
            )
        }
        templateMy { dataRepository.getTraining(id) }
    }

    private fun setSpeech(speech: Speech, item: Any?) {
        templateMy { dataRepository.setSpeech(speech, item) as Training } }
    private fun deleteTraining(trainingId: Long){
        templateNothing { dataRepository.deleteTrainingNothing(trainingId) } }
    private fun changeNameTraining(training: Training, name: String){
        templateMy { dataRepository.changeNameTraining(training, name) } }
    private fun copyExercise(trainingId: Long, exerciseId: Long){
        templateMy { dataRepository.copyExercise(trainingId, exerciseId) } }
    private fun addExercise(roundId: Long){
        templateMy { dataRepository.getExercise( roundId, 0L ) } }
    private fun deleteExercise(trainingId: Long, exerciseId: Long){
        templateMy { dataRepository.deleteExercise(trainingId, exerciseId) } }
    private fun setActivityToExercise(exerciseId: Long, activityId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { dataRepository.setActivityToExercise(exerciseId, activityId) }
                .fold(
                    onSuccess = {
                        _trainingScreenState.update { currentState ->
                            currentState.copy(
                                training = it,
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
    private fun templateMy( funDataRepository:() -> Training){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { funDataRepository() }.fold(
                onSuccess = { _trainingScreenState.update { currentState ->
                    currentState.copy( training = it, enteredName = mutableStateOf(it.name) ) } },
                onFailure = { errorApp.errorApi(it.message!!) }
            )
        }
    }
    private fun templateNothing( funDataRepository:() -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { funDataRepository() }.fold(
                onSuccess = {  },
                onFailure = { errorApp.errorApi(it.message!!) }
            )
        }
    }
}