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
            onDismissSpeechBSTraining = {},
            onDismissSpeechBSExercise = {},
            onSpeechExercise = {},
            onCopyExercise = { trainingId, exerciseId -> copyExercise(trainingId, exerciseId)},
            onDeleteExercise = { trainingId, exerciseId -> deleteExercise(trainingId, exerciseId)},
            onSave = {},
            onClickWorkout = {},
        )
    )
    val trainingScreenState: StateFlow<TrainingScreenState> = _trainingScreenState.asStateFlow()

    fun getTraining(id: Long) { templateMy { dataRepository.getTraining(id) } }

    private fun setSpeech(speech: Speech, item: Any?) {
        templateMy { dataRepository.setSpeech(speech, item) as Training } }
    private fun deleteTraining(trainingId: Long){
        templateNothing { dataRepository.deleteTrainingNothing(trainingId) } }
    private fun changeNameTraining(training: Training, name: String){
        templateMy { dataRepository.changeNameTraining(training, name) } }
    private fun copyExercise(trainingId: Long, exerciseId: Long){
        templateMy { dataRepository.copyExercise(trainingId, exerciseId) } }
    private fun deleteExercise(trainingId: Long, exerciseId: Long){
        templateMy { dataRepository.deleteExercise(trainingId, exerciseId) } }
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