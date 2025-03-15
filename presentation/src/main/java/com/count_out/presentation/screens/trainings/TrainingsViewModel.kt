package com.count_out.presentation.screens.trainings

import androidx.lifecycle.viewModelScope
import com.count_out.domain.entity.workout.Training
import com.count_out.domain.use_case.trainings.CopyTrainingUC
import com.count_out.domain.use_case.trainings.DeleteTrainingUC
import com.count_out.domain.use_case.trainings.GetTrainingsUC
import com.count_out.domain.use_case.trainings.SelectTrainingUC
import com.count_out.presentation.models.TrainingImpl
import com.count_out.presentation.screens.prime.Event
import com.count_out.presentation.screens.prime.PrimeViewModel
import com.count_out.presentation.screens.prime.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel class TrainingsViewModel @Inject constructor(
    private val converter: TrainingsConvertor,
    private val copyTraining: CopyTrainingUC,
    private val delTraining: DeleteTrainingUC,
    private val getTrainings: GetTrainingsUC,
    private val selectTraining: SelectTrainingUC,
): PrimeViewModel<TrainingsState, ScreenState<TrainingsState>>() {
    override fun initState(): ScreenState<TrainingsState> = ScreenState.Loading

    override fun routeEvent(event: Event) {
        when (event) {
            is TrainingsEvent.BackScreen -> { navigate.backStack()}
            is TrainingsEvent.Run -> { navigate.goToScreenExecuteWorkout(event.id)}
            is TrainingsEvent.Edit -> { navigate.goToScreenTraining(event.id) }
            is TrainingsEvent.Gets -> { getTrainings() }
            is TrainingsEvent.Copy -> { copyTraining(event.training) }
            is TrainingsEvent.Del -> { deleteTraining(event.training) }
            is TrainingsEvent.Select -> { selectTraining(event.training) }
        }
    }

    private fun getTrainings(){
        viewModelScope.launch(Dispatchers.IO) {
            getTrainings.execute( GetTrainingsUC.Request)
                .map { converter.convert(it) }.collect { submitState(it) }
        } }
    private fun deleteTraining(training: Training){
        viewModelScope.launch(Dispatchers.IO) {
            delTraining.execute( DeleteTrainingUC.Request(training))
                .map { converter.convert(it) }.collect { submitState(it) }
        }}
    private fun copyTraining(training: Training){
        viewModelScope.launch(Dispatchers.IO) {
            copyTraining.execute( CopyTrainingUC.Request(training))
                .map { converter.convert(it) }.collect { submitState(it) }
        } }
    private fun selectTraining(training: Training){
        viewModelScope.launch(Dispatchers.IO) {
            selectTraining.execute( SelectTrainingUC.Request(training))
                .map { converter.convert(it) }.collect { submitState(it) }
        }}

}