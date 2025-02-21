package com.count_out.domain.entity

import com.count_out.domain.entity.enums.RoundType

interface Round {
    val idRound: Long
    val trainingId: Long
    val speechId: Long
    val roundType: RoundType
    val speech: SpeechKit?
    val exercise: List<Exercise>
    val amount: Int
    val duration: Parameter
}
