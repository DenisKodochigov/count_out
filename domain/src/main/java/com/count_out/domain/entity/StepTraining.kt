package com.count_out.domain.entity

import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Round
import com.count_out.domain.entity.workout.Set

interface StepTraining {
    val round: Round?
    val exercise: Exercise?
    val nextExercise: NextExercise?
    val numberExercise: Int
    val quantityExercise: Int
    var currentSet: Set?
    val numberSet: Int
    val quantitySet: Int
}