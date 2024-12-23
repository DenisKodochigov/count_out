package com.example.count_out.ui.modules

data class NextExercise(
    val nextActivityName: String = "",
    val nextExerciseId: Long = 0,
    val nextExerciseQuantitySet: Int = 0,
    val nextExerciseSummarizeSet: List<Pair<String, Int>> = emptyList(),
)