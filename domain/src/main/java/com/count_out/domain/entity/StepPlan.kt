package com.count_out.domain.entity

import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Part
import com.count_out.domain.entity.workout.Set

interface StepPlan: Domain {
    val idPlan: Long
    val namePlan: String
    val part: Part?
    val exercise: Exercise?
    var nextExercise: NextExercise?
    val numberRing: Int
    val quantityRing: Int
    val numberExercise: Int
    val quantityExercise: Int
    val numberSet: Int
    val quantitySet: Int
    var currentSet: Set?
}