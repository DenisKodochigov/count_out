package com.example.count_out.ui.screens.training

import androidx.lifecycle.viewModelScope
import com.example.count_out.domain.use_case.activity.SelectActivityUC
import com.example.count_out.domain.use_case.activity.SetColorActivityUC
import com.example.count_out.domain.use_case.activity.UpdateActivityUC
import com.example.count_out.domain.use_case.exercise.AddExerciseUC
import com.example.count_out.domain.use_case.exercise.ChangeSequenceExerciseUC
import com.example.count_out.domain.use_case.exercise.CopyExerciseUC
import com.example.count_out.domain.use_case.exercise.DeleteExerciseUC
import com.example.count_out.domain.use_case.set.AddSetUC
import com.example.count_out.domain.use_case.set.CopySetUC
import com.example.count_out.domain.use_case.set.DeleteSetUC
import com.example.count_out.domain.use_case.set.UpdateSetUC
import com.example.count_out.domain.use_case.trainings.DeleteTrainingUC
import com.example.count_out.domain.use_case.trainings.GetTrainingUC
import com.example.count_out.domain.use_case.trainings.UpdateTrainingUC
import com.example.count_out.entity.workout.ActionWithActivity
import com.example.count_out.entity.workout.ActionWithSet
import com.example.count_out.entity.workout.Activity
import com.example.count_out.entity.workout.DataForChangeSequence
import com.example.count_out.entity.workout.Exercise
import com.example.count_out.entity.workout.Training
import com.example.count_out.ui.screens.prime.Event
import com.example.count_out.ui.screens.prime.PrimeViewModel
import com.example.count_out.ui.screens.prime.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel class TrainingViewModel @Inject constructor(
    private val converter: TrainingConverter,
    private val getTraining: GetTrainingUC,
    private val delTraining: DeleteTrainingUC,
    private val updateTraining: UpdateTrainingUC,
    private val addExercise: AddExerciseUC,
    private val copyExercise: CopyExerciseUC,
    private val delExercise: DeleteExerciseUC,
    private val changeSequenceExercise: ChangeSequenceExerciseUC,
    private val selectActivity: SelectActivityUC,
    private val setColorActivity: SetColorActivityUC,
    private val updateActivity: UpdateActivityUC,
    private val addSet: AddSetUC,
    private val copySet: CopySetUC,
    private val deleteSet: DeleteSetUC,
    private val changeSet: UpdateSetUC,
): PrimeViewModel<TrainingState, ScreenState<TrainingState>>() {

    override fun initState(): ScreenState<TrainingState> = ScreenState.Loading

    override fun routeEvent(event: Event) {
        when (event) {
            is TrainingEvent.BackScreen -> { navigate.backStack()}
            is TrainingEvent.GetTraining -> { getTraining(event.id) }
            is TrainingEvent.DelTraining -> { delTraining(event.training) }
            is TrainingEvent.UpdateTraining -> { updateTraining(event.training)}
            is TrainingEvent.AddExercise -> { addExercise(event.exercise) }
            is TrainingEvent.CopyExercise -> { copyExercise(event.exercise) }
            is TrainingEvent.DelExercise -> { deleteExercise(event.exercise) }
            is TrainingEvent.ChangeSequenceExercise -> { changeSequenceExercise(event.item) }
            is TrainingEvent.SelectActivity -> { selectActivity(event.activity) }
            is TrainingEvent.SetColorActivity -> { setColorActivity(event.activity) }
            is TrainingEvent.UpdateActivity -> { updateActivity(event.activity) }
            is TrainingEvent.AddSet -> { addSet(event.item) }
            is TrainingEvent.CopySet -> { copySet(event.item) }
            is TrainingEvent.DeleteSet -> { deleteSet(event.item) }
            is TrainingEvent.ChangeSet -> { changeSet(event.item) }
        }
    }
    fun getTraining(id: Long) {
        viewModelScope.launch {
            getTraining.execute( GetTrainingUC.Request(id))
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
    private fun addExercise(exercise: Exercise){
        viewModelScope.launch {
            addExercise.execute( AddExerciseUC.Request(exercise))
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
    private fun selectActivity(activity: ActionWithActivity){
        viewModelScope.launch {
            selectActivity.execute( SelectActivityUC.Request(activity))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }
    private fun setColorActivity(activity: Activity){
        viewModelScope.launch {
            setColorActivity.execute( SetColorActivityUC.Request(activity))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }
    private fun updateActivity(activity: Activity){
        viewModelScope.launch {
            updateActivity.execute( UpdateActivityUC.Request(activity))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }
    private fun addSet(item:ActionWithSet){
        viewModelScope.launch {
            addSet.execute( AddSetUC.Request(item))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }
    private fun copySet(item: ActionWithSet){
        viewModelScope.launch {
            copySet.execute( CopySetUC.Request(item))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }
    private fun deleteSet(item:ActionWithSet){
        viewModelScope.launch {
            deleteSet.execute( DeleteSetUC.Request(item))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }
    private fun changeSet(item:ActionWithSet){
        viewModelScope.launch {
            changeSet.execute( UpdateSetUC.Request(item))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }
}