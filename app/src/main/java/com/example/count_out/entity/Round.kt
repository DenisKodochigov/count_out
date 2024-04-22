package com.example.count_out.entity

interface Round {
    val idRound: Long
    val countRing: Int
    val trainingId: Long
    val speechId: Long
    val speech: SpeechKit
    val roundType: RoundType
    val exercise: List<Exercise>
    val sequenceExercise: String
}
