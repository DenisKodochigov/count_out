package com.example.count_out.entity.ui

import com.example.count_out.entity.workout.Activity

data class ExecuteInfoExercise(
    val activity: Activity? = null,
    val nextExercise: NextExercise? = null,
    val quantityExercise: Int = 0,
    val currentExercise: Int = 0,
    val countRing: Int = 0,
    val currentRing: Int = 0,
)