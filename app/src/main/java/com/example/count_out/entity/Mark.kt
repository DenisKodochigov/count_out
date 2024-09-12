package com.example.count_out.entity

data class Mark (
    val idTraining: Int = 0,
    val idRound: Int = 0,
    val idExercise: Int = 0,
    val idSet: Int = 0,
    val set: Byte = 0, // 1 - Begin set, 0-end set.
    val rest: Byte = 0, // 1 - begin reps, 0 - none reps
)