package com.count_out.entity.entity.workout

import com.count_out.entity.enums.RoundType

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
