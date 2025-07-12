package com.count_out.domain.entity

import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Round
import com.count_out.domain.entity.workout.Set

interface StepPlan {
    val idPlan: Long
    val namePlan: String
    val round: Round?
    val exercise: Exercise?
    var nextExercise: NextExercise?
    val numberExercise: Int
    val quantityExercise: Int
    var currentSet: Set?
    val numberSet: Int
    val quantitySet: Int
}