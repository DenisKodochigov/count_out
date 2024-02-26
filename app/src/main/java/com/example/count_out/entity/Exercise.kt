package com.example.count_out.entity

interface Exercise {
    val idExercise: Long
    val roundId: Long
    val activity: Activity
    val activityId: Long
    val speechId: Long
    val speech: Speech
    val sets: List<Set>
    val sequenceNumber: Int
}
