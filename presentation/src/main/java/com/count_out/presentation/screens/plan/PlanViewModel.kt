package com.count_out.presentation.screens.plan

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.count_out.domain.entity.SetViewId
import com.count_out.domain.entity.supportive.NameId
import com.count_out.domain.entity.types_domai.LongDm
import com.count_out.domain.entity.workout.Collapsing
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Set
import com.count_out.domain.entity.workout.ShowBottomSheet
import com.count_out.domain.entity.workout.Speech
import com.count_out.domain.use_case.other.CollapsingUC
import com.count_out.domain.use_case.other.ShowBottomSheetUC
import com.count_out.domain.use_case.plans.GetPlanUC
import com.count_out.domain.use_case.plans.UpdateNamePlanUC
import com.count_out.domain.use_case.plans.activity.GetActivitiesUC
import com.count_out.domain.use_case.plans.exercise.ChangeSequenceExerciseUC
import com.count_out.domain.use_case.plans.exercise.CopyExerciseUC
import com.count_out.domain.use_case.plans.exercise.DeleteExerciseUC
import com.count_out.domain.use_case.plans.exercise.UpdateExerciseUC
import com.count_out.domain.use_case.plans.set.CopySetUC
import com.count_out.domain.use_case.plans.set.DeleteSetUC
import com.count_out.domain.use_case.plans.set.UpdateSetUC
import com.count_out.domain.use_case.speech.UpdateSpeechUC
import com.count_out.presentation.screens.prime.Event
import com.count_out.presentation.screens.prime.PrimeViewModel
import com.count_out.presentation.screens.prime.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel class PlanViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getPlanUC: GetPlanUC,
    private val getActivitiesUC: GetActivitiesUC,
    private val updateNamePlanUC: UpdateNamePlanUC,
    private val copyExerciseUC: CopyExerciseUC,
    private val delExerciseUC: DeleteExerciseUC,
    private val updateExerciseUC: UpdateExerciseUC,
    private val changeSequenceExerciseUC: ChangeSequenceExerciseUC,
    private val copySetUC: CopySetUC,
    private val deleteSetUC: DeleteSetUC,
    private val changeSetUC: UpdateSetUC,
    private val showBottomSheetUC: ShowBottomSheetUC,
    private val collapsingSetUC: CollapsingUC,
    private val updateSpeechUC: UpdateSpeechUC,
): PrimeViewModel<PlanState, PlanConverter>() {

    override fun initScreenState(): ScreenState<PlanState> = ScreenState.Loading
    override fun initDataState(): PlanState = PlanState(event = { submitEvent(it)})
    override fun convertor(): PlanConverter = PlanConverter()

    override fun routeEvent(event: Event) {
        when (event) {
            is PlanEvent.UpdatePlanName -> { updatePlan(event.nameID)}
            is PlanEvent.CopyExercise -> { copyExercise(event.exercise) }
            is PlanEvent.DelExercise -> { deleteExercise(event.exercise) }
            is PlanEvent.UpdateExercise -> { updateExercise(event.exercise) }
            is PlanEvent.ChangeSequenceExercise -> { changeSequenceExercise(event.item) }
            is PlanEvent.CopySet -> { copySet(event.item) }
            is PlanEvent.DeleteSet -> { deleteSet(event.item) }
            is PlanEvent.UpdateSet -> { changeSet(event.item) }
            is PlanEvent.ShowBS -> { showBottomSheet(event.item) }
            is PlanEvent.SetCollapsing -> { collapsingSet(event.item) }
            is PlanEvent.UpdateSpeech -> { updateSpeech(event.item) }
        }
    }
    init{
        val planId: Long? = savedStateHandle["arg1"]
        planId?.let{ getPlan(it)}
        getActivities()
//        subscribeSequenceExercise()
    }

    fun getPlan(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            getPlanUC.execute( GetPlanUC.Request(idPlan = LongDm(id)))
                 .collect { submitState( it ) }
        }
    }
    fun getActivities() {
        viewModelScope.launch(Dispatchers.IO) {
            getActivitiesUC.execute( GetActivitiesUC.Request).collect { submitState( it ) }
        }
    }
    private fun updatePlan(nameId: NameId){
        viewModelScope.launch(Dispatchers.IO) {
            updateNamePlanUC.execute( UpdateNamePlanUC.Request(nameId)).collect {
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
    private fun updateSpeech(item: Speech){
        viewModelScope.launch(Dispatchers.IO) {
            updateSpeechUC.execute( UpdateSpeechUC.Request(item)).collect { submitState( it ) }
        }
    }
}

