package com.count_out.domain.entity

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