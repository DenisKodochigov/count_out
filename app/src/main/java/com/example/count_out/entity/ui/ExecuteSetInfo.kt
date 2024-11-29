package com.example.count_out.entity.ui

data class ExecuteSetInfo(
    val activityName: String ="",
    val typeDescription: Boolean? = null,
    val countSet: Int = 0,
    val currentIndexSet: Int = 0,
    val nextActivityName: String = "",
    val nextExercise: Long = 0,
    val countRing: Int = 0,
    val currentRing: Int = 0,
)
