package com.count_out.entity.entity

interface NextExercise {
    val nextActivityName: String
    val nextExerciseId: Long
    val nextExerciseQuantitySet: Int
    val nextExerciseSummarizeSet: List<Pair<String, Int>>
}