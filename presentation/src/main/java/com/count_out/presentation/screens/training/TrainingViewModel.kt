package com.count_out.presentation.screens.training

import androidx.lifecycle.viewModelScope
import com.count_out.domain.entity.SetViewId
import com.count_out.domain.entity.workout.Collapsing
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Set
import com.count_out.domain.entity.workout.ShowBottomSheet
import com.count_out.domain.entity.workout.SpeechKit
import com.count_out.domain.entity.workout.Training
import com.count_out.domain.use_case.other.CollapsingUC
import com.count_out.domain.use_case.other.ShowBottomSheetUC
import com.count_out.domain.use_case.plans.GetTrainingUC
import com.count_out.domain.use_case.plans.UpdateTrainingUC
import com.count_out.domain.use_case.plans.activity.GetActivitiesUC
import com.count_out.domain.use_case.plans.exercise.ChangeSequenceExerciseUC
import com.count_out.domain.use_case.plans.exercise.CopyExerciseUC
import com.count_out.domain.use_case.plans.exercise.DeleteExerciseUC
import com.count_out.domain.use_case.plans.exercise.UpdateExerciseUC
import com.count_out.domain.use_case.plans.set.CopySetUC
import com.count_out.domain.use_case.plans.set.DeleteSetUC
import com.count_out.domain.use_case.plans.set.UpdateSetUC
import com.count_out.domain.use_case.speech.UpdateSpeechKitUC
import com.count_out.presentation.models.TrainingImplP
import com.count_out.presentation.screens.prime.Event
import com.count_out.presentation.screens.prime.PrimeViewModel
import com.count_out.presentation.screens.prime.ScreenState
import com.count_out.presentation.screens.settings.SettingsEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel class TrainingViewModel @Inject constructor(
    private val getTrainingUC: GetTrainingUC,
    private val getActivitiesUC: GetActivitiesUC,
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
    private val updateSpeechKitUC: UpdateSpeechKitUC,
): PrimeViewModel<TrainingState, TrainingConverter>() {

    override fun initScreenState(): ScreenState<TrainingState> = ScreenState.Loading
    override fun initDataState(): TrainingState = TrainingState(event = { submitEvent(it)})
    override fun convertor(): TrainingConverter = TrainingConverter()

    override fun routeEvent(event: Event) {
        when (event) {
            is TrainingEvent.BackScreen -> { navigate.backStack()}
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
            is TrainingEvent.UpdateSpeech -> { updateSpeechKit(event.item) }
            is TrainingEvent.Init -> { init(event.item) }
        }
    }
    fun init( item: Long){
        getTraining(item)
        getActivities()
//        subscribeSequenceExercise()
    }

    fun getTraining(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            getTrainingUC.execute( GetTrainingUC.Request(TrainingImplP(idTraining = id)))
                 .collect { submitState( it ) }
        }
    }
    fun getActivities() {
        viewModelScope.launch(Dispatchers.IO) {
            getActivitiesUC.execute( GetActivitiesUC.Request).collect { submitState( it ) }
        }
    }
    private fun updateTraining(training: Training){
        viewModelScope.launch(Dispatchers.IO) {
            updateTrainingUC.execute( UpdateTrainingUC.Request(training)).collect {
                submitState( it ) }
        }
    }
    private fun changeSequenceExercise(item: SetViewId){
        viewModelScope.launch(Dispatchers.IO) {
            changeSequenceExerciseUC.execute( ChangeSequenceExerciseUC.Request(item)).collect { submitState( it ) }
        }
    }
    private fun copyExercise(exercise: Exercise){
        viewModelScope.launch(Dispatchers.IO) {
            copyExerciseUC.execute( CopyExerciseUC.Request(exercise)).collect { submitState( it ) }
        }
    }
    private fun deleteExercise(exercise: Exercise){
        viewModelScope.launch(Dispatchers.IO) {
            delExerciseUC.execute( DeleteExerciseUC.Request(exercise)).collect { submitState( it ) }
        }
    }
    private fun updateExercise(exercise: Exercise){
        viewModelScope.launch(Dispatchers.IO) {
            updateExerciseUC.execute( UpdateExerciseUC.Request(exercise)).collect { submitState( it ) }
        }
    }
    private fun copySet(item: Set){
        viewModelScope.launch(Dispatchers.IO) {
            copySetUC.execute( CopySetUC.Request(item)).collect { submitState( it ) }
        }
    }
    private fun deleteSet(item: Set){
        viewModelScope.launch(Dispatchers.IO) {
            deleteSetUC.execute( DeleteSetUC.Request(item)).collect { submitState( it ) }
        }
    }
    private fun changeSet(item: Set){
        viewModelScope.launch(Dispatchers.IO) {
            changeSetUC.execute( UpdateSetUC.Request(item)).collect { submitState( it ) }
        }
    }
    private fun showBottomSheet(item: ShowBottomSheet){
        viewModelScope.launch(Dispatchers.IO) {
            showBottomSheetUC.execute( ShowBottomSheetUC.Request(item)).collect { submitState( it ) }
        }
    }
    private fun collapsingSet(item: Collapsing){
        viewModelScope.launch(Dispatchers.IO) {
            collapsingSetUC.execute( CollapsingUC.Request(item)).collect { submitState( it ) }
        }
    }
    private fun updateSpeechKit(item: SpeechKit){
        viewModelScope.launch(Dispatchers.IO) {
            updateSpeechKitUC.execute( UpdateSpeechKitUC.Request(item)).collect { submitState( it ) }
        }
    }
}

