package com.example.count_out.entity.workout

import com.example.count_out.ui.modules.NextExercise

data class StepTraining (
    val round: Round? = null,
    val exercise: Exercise? = null,
    val nextExercise: NextExercise? = null,
    val numberExercise: Int = 0,
    val quantityExercise: Int = 0,
    var currentSet: Set? = null,
    val numberSet: Int = 0,
    val quantitySet: Int = 0,
)