package com.example.domain.entity

interface Exercise {
    val idExercise: Long
    val roundId: Long
    val ringId: Long
    val idView: Int
    val activity: Activity?
    val activityId: Long
    val speechId: Long
    val speech: SpeechKit?
    val sets: List<Set>
}
