package com.count_out.presentation.screens.plans.model

import com.count_out.domain.entity.SetViewId
import com.count_out.domain.entity.supportive.NameId
import com.count_out.domain.entity.workout.Collapsing
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Plan
import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.entity.workout.Selecting
import com.count_out.domain.entity.workout.Set
import com.count_out.domain.entity.workout.Speech
import com.count_out.presentation.models.LauncherBSp
import com.count_out.presentation.screens.prime.Event

sealed class PlansEvent: Event {
    data object GetPlans: PlansEvent()
    data class GetPlan(val item: Plan): PlansEvent()
    data class RunPlan(val item: Plan): PlansEvent()
    data class EditPlan(val item: Long): PlansEvent()
    data class UpdatePlanName(val nameID: NameId) : PlansEvent()
    data class DelPlan(val plan: Plan) : PlansEvent()
    data class CopyPlan(val item: Plan) : PlansEvent()
    data class UpdateSpeech(val item: Speech) : PlansEvent()

    data class CopyRing(val ring: Ring): PlansEvent()
    data class DelRing(val ring: Ring): PlansEvent()
    data class UpdateRing(val ring: Ring): PlansEvent()
    data class ChangeSequenceRing(val item: SetViewId): PlansEvent()
    data class RingOrExercise(val ring: Ring): PlansEvent()

    data class CopyExercise(val exercise: Exercise): PlansEvent()
    data class AddExercise(val exercise: Exercise): PlansEvent()
    data class DelExercise(val exercise: Exercise): PlansEvent()
    data class UpdateExercise(val exercise: Exercise): PlansEvent()
    data class ChangeSequenceExercise(val item: SetViewId): PlansEvent()

    data class CopySet(val item: Set): PlansEvent()
    data class DeleteSet(val item: Set): PlansEvent()
    data class UpdateSet(val item: Set): PlansEvent()
    data class ChangeGoal(val item: Set): PlansEvent()
    data class ChangeZone(val item: Set): PlansEvent()
//    data class ShowBS(val item: ShowBottomSheet): PlanEvent()
    data class Launcher(val item: LauncherBSp): PlansEvent()
    data class SetCollapsing(val item: Collapsing): PlansEvent()
    data class SetSelecting(val item: Selecting): PlansEvent()
    data class InitSelecting(val item: Selecting): PlansEvent()

}