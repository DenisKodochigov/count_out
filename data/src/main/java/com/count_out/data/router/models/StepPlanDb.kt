package com.count_out.data.router.models

import com.count_out.data.models.ExerciseDb
import com.count_out.data.models.NextExerciseDb
import com.count_out.data.models.PartDb
import com.count_out.data.models.RingDb
import com.count_out.domain.entity.workout.Set

data class StepPlanDb(
    val idPlan: Long,
    val namePlan: String,
    val part: PartDb?,
    val ring: RingDb?,
    val exercise: ExerciseDb?,
    var nextExercise: NextExerciseDb?,
    val numberExercise: Int,
    val quantityExercise: Int,
    var currentSet: Set?,
    val numberSet: Int,
    val quantitySet: Int,
)