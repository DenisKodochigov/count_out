package com.example.count_out.entity.workout

import com.example.count_out.entity.enums.RoundType
import com.example.count_out.entity.workout.speech.SpeechKit

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
