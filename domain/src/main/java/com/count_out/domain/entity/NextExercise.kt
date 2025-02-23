package com.count_out.domain.entity

interface NextExercise {
    val nextActivityName: String
    val nextExerciseId: Long
    val nextExerciseQuantitySet: Int
    val nextExerciseSummarizeSet: List<Pair<String, Int>>
}