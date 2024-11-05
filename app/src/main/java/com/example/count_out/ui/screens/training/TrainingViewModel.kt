package com.example.count_out.ui.screens.training

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.count_out.data.DataRepository
import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.data.room.tables.TrainingDB
import com.example.count_out.entity.MessageApp
import com.example.count_out.entity.speech.SpeechKit
import com.example.count_out.entity.workout.Set
import com.example.count_out.entity.workout.Training
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
    private val messageApp: MessageApp,
    private val dataRepository: DataRepository
): ViewModel() {
    private val _trainingScreenState = MutableStateFlow(
        TrainingScreenState(
            training = TrainingDB(),
            enteredName = mutableStateOf(""),
            changeNameTraining = { training, name -> changeNameTraining(training, name) },
            onDeleteTraining = { trainingId -> deleteTraining(trainingId) },
            onConfirmationSpeech = { speech, item -> setSpeech( speech, item ) },
            onAddExercise = { roundId, set -> addExercise( roundId, set )},
            onCopyExercise = { trainingId, exerciseId -> copyExercise(trainingId, exerciseId)},
            onDeleteExercise = { trainingId, exerciseId -> deleteExercise(trainingId, exerciseId)},
            changeSequenceExercise = { trainingId, roundId, from, to ->
                    changeSequenceExercise(trainingId,roundId, from, to)},
            onSelectActivity = {
                    exerciseId, activityId -> setActivityToExercise(exerciseId, activityId) },
            onSetColorActivity = {
                    activityId, color -> onSetColorActivity(activityId = activityId, color = color) },
            onCopySet = { trainingId, setId -> copySet(trainingId, setId)},
            onDeleteSet = { trainingId, setId -> deleteSet(trainingId, setId)},
            onAddUpdateSet = { idExercise, set -> addUpdateSet(idExercise, set) },
            onChangeSet = { set -> addUpdateSet(set.exerciseId, set) },
        )
    )
    val trainingScreenState: StateFlow<TrainingScreenState> = _trainingScreenState.asStateFlow()

    fun getTraining(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { dataRepository.getActivities() }.fold(
                onSuccess = {
                    _trainingScreenState.update { currentState -> currentState.copy(activities = it) } },
                onFailure = { messageApp.errorApi(it.message ?: "") }
            )
        }
        templateMy { dataRepository.getTraining(id) }
    }
    private fun addUpdateSet(exerciseId:Long, set: Set){
        templateMy { dataRepository.addUpdateSet(
            trainingScreenState.value.training.idTraining, exerciseId, set) } }
    private fun copySet(trainingId:Long, setId: Long){
        templateMy { dataRepository.copySet( trainingId, setId) } }
    private fun deleteSet(trainingId:Long, setId: Long){
        templateMy { dataRepository.deleteSet( trainingId, setId) } }
    private fun setSpeech(speech: SpeechKit, item: Any?) {
        templateMy { dataRepository.setSpeech(
            trainingScreenState.value.training.idTraining,speech, item) } }
    private fun deleteTraining(trainingId: Long){
        templateNothing { dataRepository.deleteTrainingNothing(trainingId) } }
    private fun changeNameTraining(training: Training, name: String){
        templateMy { dataRepository.changeNameTraining(training, name) } }
    private fun copyExercise(trainingId: Long, exerciseId: Long){
        templateMy { dataRepository.copyExercise(trainingId, exerciseId) } }
    private fun addExercise(roundId: Long, set: SetDB){
        templateMy { dataRepository.addExercise(trainingScreenState.value.training.idTraining, roundId ) } }
    private fun deleteExercise(trainingId: Long, exerciseId: Long){
        templateMy { dataRepository.deleteExercise(trainingId, exerciseId) } }
    private fun changeSequenceExercise(trainingId: Long, roundId: Long, idViewForm:Int, idViewTo: Int){
        templateMy { dataRepository.changeSequenceExercise(trainingId, roundId, idViewForm, idViewTo) } }
    private fun setActivityToExercise(exerciseId: Long, activityId: Long) {
        templateMy{dataRepository.setActivityToExercise(
            trainingScreenState.value.training.idTraining, exerciseId, activityId)}
    }
    private fun onSetColorActivity(activityId: Long, color: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { dataRepository.onSetColorActivity( activityId, color) }
                .fold( onSuccess = { }, onFailure = { messageApp.errorApi(it.message ?: "") })
        }
    }
    private fun templateMy( funDataRepository:() -> Training){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { funDataRepository() }.fold(
                onSuccess = { _trainingScreenState.update { currentState ->
                    currentState.copy(
                        training = it,
                        showBottomSheetSelectActivity = mutableStateOf(false),
                        enteredName = mutableStateOf(it.name) ) } },
                onFailure = { messageApp.errorApi(it.message ?: "") }
            )
        }
    }
    private fun templateNothing( funDataRepository:() -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { funDataRepository() }.fold(
                onSuccess = {  },
                onFailure = { messageApp.errorApi(it.message ?: "") }
            )
        }
    }
}