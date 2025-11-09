package com.count_out.presentation.screens.plan

import androidx.lifecycle.SavedStateHandle
import com.count_out.domain.entity.SetViewId
import com.count_out.domain.entity.supportive.NameId
import com.count_out.domain.entity.types_domai.LongDm
import com.count_out.domain.entity.workout.Collapsing
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Plan
import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.entity.workout.Selecting
import com.count_out.domain.entity.workout.Set
import com.count_out.domain.entity.workout.Speech
import com.count_out.domain.use_case.other.CollapsingUC
import com.count_out.domain.use_case.other.LauncherBottomSheetUC
import com.count_out.domain.use_case.plans.DeletePlanUC
import com.count_out.domain.use_case.plans.GetPlanUC
import com.count_out.domain.use_case.plans.RingOrExerciseUC
import com.count_out.domain.use_case.plans.SelectingUC
import com.count_out.domain.use_case.plans.UpdateNamePlanUC
import com.count_out.domain.use_case.plans.activity.GetActivitiesUC
import com.count_out.domain.use_case.plans.exercise.ChangeSequenceExerciseUC
import com.count_out.domain.use_case.plans.exercise.CopyExerciseUC
import com.count_out.domain.use_case.plans.exercise.DeleteExerciseUC
import com.count_out.domain.use_case.plans.exercise.UpdateExerciseUC
import com.count_out.domain.use_case.plans.ring.CopyRingUC
import com.count_out.domain.use_case.plans.set.ChangeGoalUC
import com.count_out.domain.use_case.plans.set.ChangeZoneUC
import com.count_out.domain.use_case.plans.set.CopySetUC
import com.count_out.domain.use_case.plans.set.DeleteSetUC
import com.count_out.domain.use_case.plans.set.UpdateSetUC
import com.count_out.domain.use_case.speech.UpdateSpeechUC
import com.count_out.presentation.models.LauncherBSp
import com.count_out.presentation.screens.prime.Event
import com.count_out.presentation.screens.prime.PrimeViewModel
import com.count_out.presentation.screens.prime.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel class PlanViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getPlanUC: GetPlanUC,
    private val delPlanUC: DeletePlanUC,
    private val getActivitiesUC: GetActivitiesUC,
    private val updateNamePlanUC: UpdateNamePlanUC,
    private val copyRingUC: CopyRingUC,
    private val copyExerciseUC: CopyExerciseUC,
    private val delExerciseUC: DeleteExerciseUC,
    private val updateExerciseUC: UpdateExerciseUC,
    private val changeSequenceExerciseUC: ChangeSequenceExerciseUC,
    private val copySetUC: CopySetUC,
    private val deleteSetUC: DeleteSetUC,
    private val changeSetUC: UpdateSetUC,
//    private val showBottomSheetUC: ShowBottomSheetUC,
    private val collapsingSetUC: CollapsingUC,
    private val updateSpeechUC: UpdateSpeechUC,
    private val ringOrExercise: RingOrExerciseUC,
    private val selectingUC: SelectingUC,
    private val changeZoneUC: ChangeZoneUC,
    private val changeGoalUC: ChangeGoalUC,
    private val launcherBSUC: LauncherBottomSheetUC,

    ): PrimeViewModel<PlanState, PlanConverter>() { //ManagerBottomSheetUC

    override fun initScreenState(): ScreenState<PlanState> = ScreenState.Loading
    override fun initDataState(): PlanState = PlanState(event = { submitEvent(it)})
    override fun convertor(): PlanConverter = PlanConverter()

    override fun routeEvent(event: Event) {
        when (event) {
            is PlanEvent.UpdatePlanName -> { updatePlan(event.nameID)}
            is PlanEvent.DelPlan -> { deletePlan(event.plan)}
            is PlanEvent.CopyExercise -> { copyExercise(event.exercise) }
            is PlanEvent.CopyRing -> { copyRing(event.ring) }
            is PlanEvent.DelExercise -> { deleteExercise(event.exercise) }
            is PlanEvent.UpdateExercise -> { updateExercise(event.exercise) }
            is PlanEvent.ChangeSequenceExercise -> { changeSequenceExercise(event.item) }
            is PlanEvent.CopySet -> { copySet(event.item) }
            is PlanEvent.DeleteSet -> { deleteSet(event.item) }
            is PlanEvent.UpdateSet -> { changeSet(event.item) }
//            is PlanEvent.ShowBS -> { showBottomSheet(event.item) }
            is PlanEvent.SetCollapsing -> { collapsingSet(event.item) }
            is PlanEvent.UpdateSpeech -> { updateSpeech(event.item) }
            is PlanEvent.RingOrExercise -> { ringOrExercise(event.ring) }
            is PlanEvent.SetSelecting -> { selectingSet(event.item) }
            is PlanEvent.ChangeZone -> { changeZone(event.item) }
            is PlanEvent.ChangeGoal -> { changeGoal(event.item) }
            is PlanEvent.Launcher -> { launcherBS(event.item) }
        }
    }
    init{
        val planId: Long? = savedStateHandle["arg1"]
        planId?.let{ getPlan(it)}
        getActivities()
    }
    private fun launcherBS(manager: LauncherBSp){
        template{ launcherBSUC.execute( LauncherBottomSheetUC.Request(manager))}}
    private fun deletePlan(plan: Plan){
        template{ delPlanUC.execute( DeletePlanUC.Request(plan))}}
    private fun changeZone(item: Set) {
        template{ changeZoneUC.execute( ChangeZoneUC.Request(item))}}
    private fun changeGoal(item: Set) {
        template{ changeGoalUC.execute( ChangeGoalUC.Request(item)) } }
    private fun ringOrExercise(item: Ring) {
        template{ringOrExercise.execute( RingOrExerciseUC.Request(item)) } }
    private fun selectingSet(item: Selecting) {
        template{ selectingUC.execute( SelectingUC.Request(item)) }}
    fun getPlan(id: Long) {
        template{ getPlanUC.execute( GetPlanUC.Request(idPlan = LongDm(id)))} }
    fun getActivities() {
        template{ getActivitiesUC.execute( GetActivitiesUC.Request) } }
    private fun updatePlan(nameId: NameId){
        template{ updateNamePlanUC.execute( UpdateNamePlanUC.Request(nameId)) } }
    private fun changeSequenceExercise(item: SetViewId){
        template{ changeSequenceExerciseUC.execute( ChangeSequenceExerciseUC.Request(item))} }
    private fun copyRing(ring: Ring){
        template{copyRingUC.execute( CopyRingUC.Request(ring)) } }
    private fun copyExercise(exercise: Exercise){
        template{ copyExerciseUC.execute( CopyExerciseUC.Request(exercise)) } }
    private fun deleteExercise(exercise: Exercise){
        template{ delExerciseUC.execute( DeleteExerciseUC.Request(exercise)) } }
    private fun updateExercise(exercise: Exercise){
        template{updateExerciseUC.execute( UpdateExerciseUC.Request(exercise))} }
    private fun copySet(item: Set){
        template{copySetUC.execute( CopySetUC.Request(item)) } }
    private fun deleteSet(item: Set){
        template{deleteSetUC.execute( DeleteSetUC.Request(item))   } }
    private fun changeSet(item: Set){
        template{changeSetUC.execute( UpdateSetUC.Request(item)) } }
//    private fun showBottomSheet(item: ShowBottomSheet){
//        template{showBottomSheetUC.execute( ShowBottomSheetUC.Request(item))} }
    private fun collapsingSet(item: Collapsing){
        template{collapsingSetUC.execute( CollapsingUC.Request(item))} }
    private fun updateSpeech(item: Speech){
        template{updateSpeechUC.execute( UpdateSpeechUC.Request(item)) } }
}

