package com.count_out.presentation.screens.training

import androidx.lifecycle.viewModelScope
import com.count_out.domain.entity.DataForChangeSequence
import com.count_out.domain.entity.workout.Collapsing
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Set
import com.count_out.domain.entity.workout.ShowBottomSheet
import com.count_out.domain.entity.workout.Training
import com.count_out.domain.use_case.exercise.ChangeSequenceExerciseUC
import com.count_out.domain.use_case.exercise.CopyExerciseUC
import com.count_out.domain.use_case.exercise.DeleteExerciseUC
import com.count_out.domain.use_case.exercise.UpdateExerciseUC
import com.count_out.domain.use_case.other.CollapsingUC
import com.count_out.domain.use_case.other.ShowBottomSheetUC
import com.count_out.domain.use_case.set.CopySetUC
import com.count_out.domain.use_case.set.DeleteSetUC
import com.count_out.domain.use_case.set.UpdateSetUC
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
    private val getTrainingUC: GetTrainingUC,
    private val updateTrainingUC: UpdateTrainingUC,
    private val copyExerciseUC: CopyExerciseUC,
    private val delExerciseUC: DeleteExerciseUC,
    private val updateExerciseUC: UpdateExerciseUC,
    private val changeSequenceExerciseUC: ChangeSequenceExerciseUC,
    private val copySetUC: CopySetUC,
    private val deleteSetUC: DeleteSetUC,
    private val changeSetUC: UpdateSetUC,
    private val showBottomSheetUC: ShowBottomSheetUC,
    private val collapsingSetUC: CollapsingUC,
): PrimeViewModel<TrainingState, ScreenState<TrainingState>>() {

    override fun initState(): ScreenState<TrainingState> = ScreenState.Loading

    override fun routeEvent(event: Event) {
        when (event) {
            is TrainingEvent.BackScreen -> { navigate.backStack()}
            is TrainingEvent.GetTraining -> { getTraining(event.id) }
            is TrainingEvent.UpdateTraining -> { updateTraining(event.training)}
            is TrainingEvent.CopyExercise -> { copyExercise(event.exercise) }
            is TrainingEvent.DelExercise -> { deleteExercise(event.exercise) }
            is TrainingEvent.UpdateExercise -> { updateExercise(event.exercise) }
            is TrainingEvent.ChangeSequenceExercise -> { changeSequenceExercise(event.item) }
            is TrainingEvent.CopySet -> { copySet(event.item) }
            is TrainingEvent.DeleteSet -> { deleteSet(event.item) }
            is TrainingEvent.UpdateSet -> { changeSet(event.item) }
            is TrainingEvent.ShowBS -> { showBottomSheet(event.item) }
            is TrainingEvent.SetCollapsing -> { collapsingSet(event.item) }
        }
    }
    var idTraining: Long = 0

    fun getTraining(id: Long) {
        idTraining = id
        viewModelScope.launch {
            getTrainingUC.execute( GetTrainingUC.Request(TrainingImpl(idTraining = id)))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }

    private fun updateTraining(training: Training){
        viewModelScope.launch {
            updateTrainingUC.execute( UpdateTrainingUC.Request(training))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }
    private fun changeSequenceExercise(item: DataForChangeSequence){
        viewModelScope.launch {
            changeSequenceExerciseUC.execute( ChangeSequenceExerciseUC.Request(item))
                .map { converter.convert(it) }
            getTrainingUC.execute( GetTrainingUC.Request(TrainingImpl(idTraining = idTraining)))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }
    private fun copyExercise(exercise: Exercise){
        viewModelScope.launch {
            copyExerciseUC.execute( CopyExerciseUC.Request(exercise)).map { converter.convert(it) }
            getTrainingUC.execute( GetTrainingUC.Request(TrainingImpl(idTraining = idTraining)))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }
    private fun deleteExercise(exercise: Exercise){
        viewModelScope.launch {
            delExerciseUC.execute( DeleteExerciseUC.Request(exercise)).map { converter.convert(it) }
            getTrainingUC.execute( GetTrainingUC.Request(TrainingImpl(idTraining = idTraining)))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }
    private fun updateExercise(exercise: Exercise){
        viewModelScope.launch {
            updateExerciseUC.execute( UpdateExerciseUC.Request(exercise)).map { converter.convert(it) }
            getTrainingUC.execute( GetTrainingUC.Request(TrainingImpl(idTraining = idTraining)))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }
    private fun copySet(item: Set){
        viewModelScope.launch {
            copySetUC.execute( CopySetUC.Request(item)).map { converter.convert(it) }
            getTrainingUC.execute( GetTrainingUC.Request(TrainingImpl(idTraining = idTraining)))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }
    private fun deleteSet(item: Set){
        viewModelScope.launch {
            deleteSetUC.execute( DeleteSetUC.Request(item)).map { converter.convert(it) }
            getTrainingUC.execute( GetTrainingUC.Request(TrainingImpl(idTraining = idTraining)))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }
    private fun changeSet(item: Set){
        viewModelScope.launch {
            changeSetUC.execute( UpdateSetUC.Request(item)).map { converter.convert(it) }
            getTrainingUC.execute( GetTrainingUC.Request(TrainingImpl(idTraining = idTraining)))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }
    private fun showBottomSheet(item: ShowBottomSheet){
        viewModelScope.launch {
            showBottomSheetUC.execute( ShowBottomSheetUC.Request(item)).map { converter.convert(it) }
            getTrainingUC.execute( GetTrainingUC.Request(TrainingImpl(idTraining = idTraining)))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }
    private fun collapsingSet(item: Collapsing){
        viewModelScope.launch {
            collapsingSetUC.execute( CollapsingUC.Request(item)).map { converter.convert(it) }
            getTrainingUC.execute( GetTrainingUC.Request(TrainingImpl(idTraining = idTraining)))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }
}
//    private fun showSpeechTraining(item: Boolean){
//        viewModelScope.launch {
//            showSpeechTrainingUC.execute( ShowBSSpeechTrainingUC.Request(item))
//            getTrainingUC.execute( GetTrainingUC.Request(TrainingImpl(idTraining = idTraining)))
//                .map { converter.convert(it) }.collect { submitState(it) }
//        }
//    }
//    private fun showBSSelectActivity(item: Boolean){
//        viewModelScope.launch {
//            showBSSelectActivityUC.execute( ShowBSSelectActivityUC.Request(item))
//            getTrainingUC.execute( GetTrainingUC.Request(TrainingImpl(idTraining = idTraining)))
//                .map { converter.convert(it) }.collect { submitState(it) }
//        }
//    }
//    private fun showBSSpeechExercise(item: Boolean){
//        viewModelScope.launch {
//            showBSSpeechExerciseUC.execute( ShowBSSpeechExerciseUC.Request(item))
//                .map { converter.convert(it) }.collect { submitState(it) }
//        }
//    }
//    private fun showBSSpeechSet(item: Boolean){
//        viewModelScope.launch {
//            showBSSpeechSetUC.execute( ShowBSSpeechSetUC.Request(item))
//                .map { converter.convert(it) }.collect { submitState(it) }
//        }
//    }
//    private fun showBSSpeechWorkUp(item: Boolean){
//        viewModelScope.launch {
//            showBSSpeechWorkUpUC.execute( ShowBSSpeechWorkUpUC.Request(item))
//                .map { converter.convert(it) }.collect { submitState(it) }
//        }
//    }
//    private fun showBSSpeechWorkOut(item: Boolean){
//        viewModelScope.launch {
//            showBSSpeechWorkOutUC.execute( ShowBSSpeechWorkOutUC.Request(item))
//                .map { converter.convert(it) }.collect { submitState(it) }
//        }
//    }
//    private fun showBSSpeechWorkDown(item: Boolean){
//        viewModelScope.launch {
//            showBSSpeechWorkDownUC.execute( ShowBSSpeechWorkDownUC.Request(item))
//                .map { converter.convert(it) }.collect { submitState(it) }
//        }
//    }
//    private fun collapsingListExercise(item: List<Long>){
//        viewModelScope.launch {
//            collapsingListExerciseUC.execute( CollapsingListExerciseUC.Request(item))
//                .map { converter.convert(it) }.collect { submitState(it) }
//        }
//    }
//    private fun collapsingWorkDown(item: Boolean){
//        viewModelScope.launch {
//            collapsingWorkDownUC.execute( CollapsingWorkDownUC.Request(item))
//                .map { converter.convert(it) }.collect { submitState(it) }
//        }
//    }
//    private fun collapsingWorkOut(item: Boolean){
//        viewModelScope.launch {
//            collapsingWorkOutUC.execute( CollapsingWorkOutUC.Request(item))
//                .map { converter.convert(it) }.collect { submitState(it) }
//        }
//    }
//    private fun collapsingWorkUp(item: Boolean){
//        viewModelScope.launch {
//            collapsingWorkUpUC.execute( CollapsingWorkUpUC.Request(item))
//                .map { converter.convert(it) }.collect { submitState(it) }
//        }
//    }
