package com.example.count_out.entity

interface Round {
    val idRound: Long
    val trainingId: Long
    val speechId: Long
    val speech: Speech
    val roundType: RoundType
    val exercise: MutableList<Exercise>
}