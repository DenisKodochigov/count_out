package com.count_out.presentation.screens.trainings

import androidx.lifecycle.viewModelScope
import com.count_out.domain.entity.workout.Speech
import com.count_out.domain.entity.workout.SpeechKit
import com.count_out.domain.entity.workout.Training
import com.count_out.domain.use_case.speech.UpdateSpeechUC
import com.count_out.domain.use_case.trainings.CopyTrainingUC
import com.count_out.domain.use_case.trainings.DeleteTrainingUC
import com.count_out.domain.use_case.trainings.GetTrainingsUC
import com.count_out.domain.use_case.trainings.SelectTrainingUC
import com.count_out.domain.use_case.trainings.UpdateTrainingUC
import com.count_out.presentation.screens.prime.Event
import com.count_out.presentation.screens.prime.PrimeViewModel
import com.count_out.presentation.screens.prime.ScreenState
import com.count_out.presentation.view_element.lg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel class TrainingsViewModel @Inject constructor(
    private val copyTrainingUC: CopyTrainingUC,
    private val delTrainingUC: DeleteTrainingUC,
    private val getTrainingsUC: GetTrainingsUC,
    private val updateTrainingUC: UpdateTrainingUC,
    private val selectTrainingUC: SelectTrainingUC,
    private val updateSpeechUC: UpdateSpeechUC,
): PrimeViewModel<TrainingsState, TrainingsConvertor>() {
    override fun initScreenState(): ScreenState<TrainingsState> = ScreenState.Loading
    override fun initDataState(): TrainingsState = TrainingsState()
    override fun initConvertor(): TrainingsConvertor = TrainingsConvertor()

    override fun routeEvent(event: Event) {
        when (event) {
            is TrainingsEvent.BackScreen -> { navigate.backStack()}
            is TrainingsEvent.Run -> { navigate.goToScreenExecuteWorkout(event.item)}
            is TrainingsEvent.Edit -> { navigate.goToScreenTraining(event.item) }
            is TrainingsEvent.Gets -> { getTrainings() }
            is TrainingsEvent.Copy -> { copyTraining(event.item) }
            is TrainingsEvent.Del -> { deleteTraining(event.item) }
            is TrainingsEvent.Update -> { updateTraining(event.item) }
            is TrainingsEvent.Select -> { selectTraining(event.item) }
            is TrainingsEvent.UpdateSpeech -> { updateSpeech(event.item) }
        }
    }

    private fun getTrainings(){
        viewModelScope.launch(Dispatchers.IO) {
            getTrainingsUC.execute(GetTrainingsUC.Request).collect { submitState( it ) }
        } }
    private fun deleteTraining(training: Training){
        viewModelScope.launch(Dispatchers.IO) {
            delTrainingUC.execute( DeleteTrainingUC.Request(training)).collect { submitState( it ) }
        }}
    private fun copyTraining(training: Training){
        viewModelScope.launch(Dispatchers.IO) {
            copyTrainingUC.execute( CopyTrainingUC.Request(training)).collect { submitState( it ) }
        } }
    private fun updateTraining(training: Training){
        viewModelScope.launch(Dispatchers.IO) {
            updateTrainingUC.execute( UpdateTrainingUC.Request(training)).collect { submitState( it ) }
        }}
    private fun selectTraining(training: Training){
        viewModelScope.launch(Dispatchers.IO) {
            selectTrainingUC.execute( SelectTrainingUC.Request(training)).collect { submitState( it ) }
        }}
    private fun updateSpeech(item: SpeechKit){
        viewModelScope.launch(Dispatchers.IO) {
            updateSpeechUC.execute( UpdateSpeechUC.Request(item)).collect { submitState( it ) }
        }
    }
}