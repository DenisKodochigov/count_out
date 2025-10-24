package com.count_out.presentation.screens.plan

import com.count_out.domain.entity.SetViewId
import com.count_out.domain.entity.supportive.NameId
import com.count_out.domain.entity.workout.Collapsing
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Plan
import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.entity.workout.Set
import com.count_out.domain.entity.workout.ShowBottomSheet
import com.count_out.domain.entity.workout.Speech
import com.count_out.presentation.screens.prime.Event

sealed class PlanEvent: Event {
    data class DelPlan(val training: Plan) : PlanEvent()
    data class UpdatePlanName(val nameID: NameId) : PlanEvent()

    data class CopyRing(val ring: Ring): PlanEvent()
    data class DelRing(val ring: Ring): PlanEvent()
    data class UpdateRing(val ring: Ring): PlanEvent()
    data class ChangeSequenceRing(val item: SetViewId): PlanEvent()
    data class RingOrExercise(val ring: Ring): PlanEvent()

    data class CopyExercise(val exercise: Exercise): PlanEvent()
    data class DelExercise(val exercise: Exercise): PlanEvent()
    data class UpdateExercise(val exercise: Exercise): PlanEvent()
    data class ChangeSequenceExercise(val item: SetViewId): PlanEvent()
    data class SelectedExercise(val item: Long): PlanEvent()
    data class CopySet(val item: Set): PlanEvent()
    data class DeleteSet(val item: Set): PlanEvent()
    data class UpdateSet(val item: Set): PlanEvent()

    data class ShowBS(val item: ShowBottomSheet): PlanEvent()
    data class SetCollapsing(val item: Collapsing): PlanEvent()

    data class UpdateSpeech(val item: Speech): PlanEvent()
    data object BackScreen : PlanEvent()
    data class Init(val item: Long): PlanEvent()
}

//    data class WorkUpCollapsing(val item: Boolean): TrainingEvent()
//    data class WorkOutCollapsing(val item: Boolean): TrainingEvent()
//    data class WorkDownCollapsing(val item: Boolean): TrainingEvent()
//    data class ListCollapsingSet(val item: List<Long>): TrainingEvent()
//    data class ListCollapsingExercise(val item: List<Long>): TrainingEvent()
//    data class ShowBSSpeechTraining(val item: Boolean): TrainingEvent()
//    data class ShowBSSpeechWorkUp(val item: Boolean): TrainingEvent()
//    data class ShowBSSpeechWorkOut(val item: Boolean): TrainingEvent()
//    data class ShowBSSpeechWorkDown(val item: Boolean): TrainingEvent()
//    data class ShowBSSpeechExercise(val item: Boolean): TrainingEvent()
//    data class ShowBSSpeechSet(val item: Boolean): TrainingEvent()
//    data class ShowBSSelectActivity(val item: Boolean): TrainingEvent()