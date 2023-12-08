package com.example.count_out.entity

interface Set {
    val idSet: Long
    val roundId: Long
    val name: String
    val exercise: Exercise
    val reps: Int
    val duration: Int
    val countdown: Boolean
    val weight: Int
    val intervalReps: Int

}
