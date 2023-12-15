package com.example.count_out.entity

interface Round {
    val idRound: Long
    val workType: Int
    val trainingId: Long
    val speechId: Long
    val speechActivity: SpeechActivity
    val exercise: List<Exercise>
}