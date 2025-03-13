package com.count_out.presentation.screens.training

import androidx.lifecycle.viewModelScope
import com.count_out.domain.entity.DataForChangeSequence
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Set
import com.count_out.domain.entity.workout.Training
import com.count_out.domain.use_case.exercise.ChangeSequenceExerciseUC
import com.count_out.domain.use_case.exercise.CopyExerciseUC
import com.count_out.domain.use_case.exercise.DeleteExerciseUC
import com.count_out.domain.use_case.set.CopySetUC
import com.count_out.domain.use_case.set.DeleteSetUC
import com.count_out.domain.use_case.set.UpdateSetUC
import com.count_out.domain.use_case.trainings.DeleteTrainingUC
import com.count_out.domain.use_case.trainings.GetTrainingUC
import com.count_out.domain.use_case.trainings.UpdateTrainingUC
import com.count_out.presentation.models.TrainingImpl
import com.count_out.presentation.screens.prime.Event
import com.count_out.presentation.screens.prime.PrimeViewModel
import com.count_out.presentation.screens.prime.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel class TrainingViewModel @Inject constructor(
    private val converter: TrainingConverter,
    private val getTraining: GetTrainingUC,
    private val delTraining: DeleteTrainingUC,
    private val updateTraining: UpdateTrainingUC,
    private val copyExercise: CopyExerciseUC,
    private val delExercise: DeleteExerciseUC,
    private val changeSequenceExercise: ChangeSequenceExerciseUC,
    private val copySet: CopySetUC,
    private val deleteSet: DeleteSetUC,
    private val changeSet: UpdateSetUC,
): PrimeViewModel<TrainingState, ScreenState<TrainingState>>() {

    override fun initState(): ScreenState<TrainingState> = ScreenState.Loading

    override fun routeEvent(event: Event) {
        when (event) {
            is TrainingEvent.BackScreen -> { navigate.backStack()}
            is TrainingEvent.GetTraining -> { getTraining(event.id) }
            is TrainingEvent.UpdateTraining -> { updateTraining(event.training)}
            is TrainingEvent.CopyExercise -> { copyExercise(event.exercise) }
            is TrainingEvent.DelExercise -> { deleteExercise(event.exercise) }
            is TrainingEvent.ChangeSequenceExercise -> { changeSequenceExercise(event.item) }
            is TrainingEvent.CopySet -> { copySet(event.item) }
            is TrainingEvent.DeleteSet -> { deleteSet(event.item) }
            is TrainingEvent.ChangeSet -> { changeSet(event.item) }
        }
    }
    fun getTraining(id: Long) {
        viewModelScope.launch {
            getTraining.execute( GetTrainingUC.Request(TrainingImpl(idTraining = id)))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }
    private fun delTraining(training: Training){
        viewModelScope.launch {
            delTraining.execute( DeleteTrainingUC.Request(training))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }
    private fun updateTraining(training: Training){
        viewModelScope.launch {
            updateTraining.execute( UpdateTrainingUC.Request(training))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }
    private fun changeSequenceExercise(item: DataForChangeSequence){
        viewModelScope.launch {
            changeSequenceExercise.execute( ChangeSequenceExerciseUC.Request(item))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }
    private fun copyExercise(exercise: Exercise){
        viewModelScope.launch {
            copyExercise.execute( CopyExerciseUC.Request(exercise))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }
    private fun deleteExercise(exercise: Exercise){
        viewModelScope.launch {
            delExercise.execute( DeleteExerciseUC.Request(exercise))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }
    private fun copySet(item: Set){
        viewModelScope.launch {
            copySet.execute( CopySetUC.Request(item))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }
    private fun deleteSet(item: Set){
        viewModelScope.launch {
            deleteSet.execute( DeleteSetUC.Request(item))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }
    private fun changeSet(item: Set){
        viewModelScope.launch {
            changeSet.execute( UpdateSetUC.Request(item))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }
}