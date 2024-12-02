package com.example.count_out.entity.ui

import com.example.count_out.entity.workout.Set

data class ExecuteSetInfo(
    val activityName: String ="",
    val activityId: Long = 0L,
    val countSet: Int = 0,
    val currentSet: Set? = null,
    val currentIndexSet: Int = 0,
    val nextActivityName: String = "",
    val nextExercise: Long = 0,
    val nextExerciseSummarizeSet: List<Pair<String, Int>> = emptyList(),
    val countRing: Int = 0,
    val currentRing: Int = 0,
)
