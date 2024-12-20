package com.example.count_out.entity.ui

import com.example.count_out.entity.workout.Activity

data class ExecuteInfoExercise(
    val activity: Activity? = null,
    val nextExercise: NextExercise? = null,
    val numberExercise: Int = 0,
    val quantityExercise: Int = 0,
//    val quantityRing: Int = 0,
//    val numberRing: Int = 0,
)