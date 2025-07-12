package com.count_out.data.router.models

import com.count_out.domain.entity.NextExercise
import com.count_out.domain.entity.StepPlan
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Round
import com.count_out.domain.entity.workout.Set

data class StepPlanImpl(
    override val idPlan: Long,
    override val namePlan: String,
    override val round: Round?,
    override val exercise: Exercise?,
    override var nextExercise: NextExercise?,
    override val numberExercise: Int,
    override val quantityExercise: Int,
    override var currentSet: Set?,
    override val numberSet: Int,
    override val quantitySet: Int,
): StepPlan