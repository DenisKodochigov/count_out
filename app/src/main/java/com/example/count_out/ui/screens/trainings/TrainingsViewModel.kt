package com.example.count_out.ui.screens.trainings

import androidx.lifecycle.viewModelScope
import com.example.count_out.ui.screens.prime.Event
import com.example.count_out.ui.screens.prime.PrimeViewModel
import com.example.count_out.ui.screens.prime.ScreenState
import com.example.count_out.domain.use_case.trainings.AddTrainingUC
import com.example.count_out.domain.use_case.trainings.CopyTrainingUC
import com.example.count_out.domain.use_case.trainings.DeleteTrainingUC
import com.example.count_out.domain.use_case.trainings.GetTrainingsUC
import com.example.count_out.domain.use_case.trainings.SelectTrainingUC
import com.example.count_out.entity.workout.Training
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel class TrainingsViewModel @Inject constructor(
    private val converter: TrainingsConvertor,
    private val addTraining: AddTrainingUC,
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
            is TrainingsEvent.Add -> { addTraining() }
            is TrainingsEvent.Copy -> { copyTraining(event.training) }
            is TrainingsEvent.Del -> { deleteTraining(event.training) }
            is TrainingsEvent.Select -> { selectTraining(event.training) }
        }
    }

    private fun getTrainings(){
        viewModelScope.launch {
            getTrainings.execute( GetTrainingsUC.Request)
                .map { converter.convert(it) }.collect { submitState(it) }
        } }
    private fun addTraining(){
        viewModelScope.launch {
            addTraining.execute(AddTrainingUC.Request)
                .map { converter.convert(it) }.collect { submitState(it) }
        }}
    private fun deleteTraining(training: Training){
        viewModelScope.launch {
            delTraining.execute( DeleteTrainingUC.Request(training))
                .map { converter.convert(it) }.collect { submitState(it) }
        }}
    private fun copyTraining(training: Training){
        viewModelScope.launch {
            copyTraining.execute( CopyTrainingUC.Request(training))
                .map { converter.convert(it) }.collect { submitState(it) }
        } }
    private fun selectTraining(training: Training){
        viewModelScope.launch {
            selectTraining.execute( SelectTrainingUC.Request(training))
                .map { converter.convert(it) }.collect { submitState(it) }
        }}

}