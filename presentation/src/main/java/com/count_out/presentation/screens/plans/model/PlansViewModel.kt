package com.count_out.presentation.screens.plans.model

import androidx.lifecycle.SavedStateHandle
import com.count_out.domain.use_case.other.CollapsingUC
import com.count_out.domain.use_case.other.LauncherBottomSheetUC
import com.count_out.domain.use_case.plans.CopyPlanUC
import com.count_out.domain.use_case.plans.DeletePlanUC
import com.count_out.domain.use_case.plans.GetPlansUC
import com.count_out.domain.use_case.plans.RingOrExerciseUC
import com.count_out.domain.use_case.plans.RunPlanUC
import com.count_out.domain.use_case.plans.SelectingUC
import com.count_out.domain.use_case.plans.UpdateNamePlanUC
import com.count_out.domain.use_case.plans.activity.GetActivitiesUC
import com.count_out.domain.use_case.plans.exercise.ChangeSequenceExerciseUC
import com.count_out.domain.use_case.plans.exercise.CopyExerciseUC
import com.count_out.domain.use_case.plans.exercise.DeleteExerciseUC
import com.count_out.domain.use_case.plans.exercise.UpdateExerciseUC
import com.count_out.domain.use_case.plans.ring.ChangeSequenceRingUC
import com.count_out.domain.use_case.plans.ring.CopyRingUC
import com.count_out.domain.use_case.plans.ring.DelRingUC
import com.count_out.domain.use_case.plans.ring.UpdateRingUC
import com.count_out.domain.use_case.plans.set.ChangeGoalUC
import com.count_out.domain.use_case.plans.set.ChangeZoneUC
import com.count_out.domain.use_case.plans.set.CopySetUC
import com.count_out.domain.use_case.plans.set.DeleteSetUC
import com.count_out.domain.use_case.plans.set.UpdateSetUC
import com.count_out.domain.use_case.speech.UpdateSpeechUC
import com.count_out.presentation.screens.prime.Event
import com.count_out.presentation.screens.prime.PrimeViewModel
import com.count_out.presentation.screens.prime.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlansViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getPlansUC: GetPlansUC,
    private val copyPlanUC: CopyPlanUC,
    private val delPlanUC: DeletePlanUC,
    private val runPlanUC: RunPlanUC,
    private val updateNamePlanUC: UpdateNamePlanUC,
    private val copyRingUC: CopyRingUC,
    private val delRingUC: DelRingUC,
    private val updateRingUC: UpdateRingUC,
    private val changeSequenceRingUC: ChangeSequenceRingUC,
    private val ringOrExercise: RingOrExerciseUC,
    private val copyExerciseUC: CopyExerciseUC,
    private val delExerciseUC: DeleteExerciseUC,
    private val updateExerciseUC: UpdateExerciseUC,
    private val changeSequenceExerciseUC: ChangeSequenceExerciseUC,
    private val copySetUC: CopySetUC,
    private val deleteSetUC: DeleteSetUC,
    private val updateSetUC: UpdateSetUC,
    private val collapsingSetUC: CollapsingUC,
    private val selectingUC: SelectingUC,
    private val changeZoneUC: ChangeZoneUC,
    private val changeGoalUC: ChangeGoalUC,
    private val launcherBSUC: LauncherBottomSheetUC,
    private val updateSpeechUC: UpdateSpeechUC,


    private val getActivitiesUC: GetActivitiesUC,
): PrimeViewModel<PlansState, PlansConvertor>() {

    override fun initScreenState(): ScreenState<PlansState> = ScreenState.Loading
    override fun initDataState(): PlansState = PlansState(event = { submitEvent(it) })
    override fun convertor(): PlansConvertor = PlansConvertor()

    override fun routeEvent(event: Event) {
        when (event) {
            is PlansEvent.GetPlans -> { run(getPlansUC, GetPlansUC.Request) }
            is PlansEvent.CopyPlan -> { run(copyPlanUC, CopyPlanUC.Request(event.item))}
            is PlansEvent.DelPlan -> { run(delPlanUC, DeletePlanUC.Request(event.plan)) }
            is PlansEvent.RunPlan -> { run(runPlanUC, RunPlanUC.Request(event.item))}
            is PlansEvent.UpdatePlanName -> {
                run(updateNamePlanUC, UpdateNamePlanUC.Request(event.nameID))}
            is PlansEvent.CopyRing -> { run(copyRingUC, CopyRingUC.Request(event.ring))}
            is PlansEvent.DelRing -> { run(delRingUC, DelRingUC.Request(event.ring))}
            is PlansEvent.UpdateRing -> { run(updateRingUC, UpdateRingUC.Request(event.ring))}
            is PlansEvent.ChangeSequenceRing -> {
                run(changeSequenceRingUC, ChangeSequenceRingUC.Request(event.item))}
            is PlansEvent.RingOrExercise -> { run(ringOrExercise, RingOrExerciseUC.Request(event.ring))}
            is PlansEvent.CopyExercise -> {run(copyExerciseUC, CopyExerciseUC.Request(event.exercise))}
            is PlansEvent.DelExercise -> { run(delExerciseUC, DeleteExerciseUC.Request(event.exercise)) }
            is PlansEvent.UpdateExercise -> { run(updateExerciseUC, UpdateExerciseUC.Request(event.exercise)) }
            is PlansEvent.ChangeSequenceExercise -> {
                run(changeSequenceExerciseUC, ChangeSequenceExerciseUC.Request(event.item)) }
            is PlansEvent.CopySet -> { run(copySetUC, CopySetUC.Request(event.item)) }
            is PlansEvent.DeleteSet -> { run(deleteSetUC, DeleteSetUC.Request(event.item)) }
            is PlansEvent.UpdateSet -> { run(updateSetUC, UpdateSetUC.Request(event.item)) }
            is PlansEvent.SetCollapsing -> { run(collapsingSetUC, CollapsingUC.Request(event.item)) }
            is PlansEvent.SetSelecting -> { run(selectingUC, SelectingUC.Request(event.item)) }
            is PlansEvent.ChangeZone -> { run(changeZoneUC, ChangeZoneUC.Request(event.item)) }
            is PlansEvent.ChangeGoal -> {
                run(changeGoalUC, ChangeGoalUC.Request(event.item)) }
            is PlansEvent.Launcher -> { run(launcherBSUC, LauncherBottomSheetUC.Request(event.item)) }
            is PlansEvent.UpdateSpeech -> {
                run(updateSpeechUC, UpdateSpeechUC.Request(event.item)) }
        }
    }

    init{
        template{ getPlansUC.execute(GetPlansUC.Request) }
        template{ getActivitiesUC.execute( GetActivitiesUC.Request) }
    }
}