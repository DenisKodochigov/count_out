package com.example.domain.entity

import com.example.domain.entity.enums.RoundType

interface Round {
    val idRound: Long
    val trainingId: Long
    val speechId: Long
    val roundType: RoundType
    val speech: SpeechKit?
    val exercise: List<Exercise>
}
