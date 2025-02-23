package com.count_out.presentation.models

data class NextExercise(
    val nextActivityName: String = "",
    val nextExerciseId: Long = 0,
    val nextExerciseQuantitySet: Int = 0,
    val nextExerciseSummarizeSet: List<Pair<String, Int>> = emptyList(),
)
