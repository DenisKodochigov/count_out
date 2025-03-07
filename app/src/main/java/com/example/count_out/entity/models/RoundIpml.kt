package com.example.count_out.entity.models

import com.example.count_out.entity.enums.RoundType
import com.example.count_out.entity.workout.Exercise
import com.example.count_out.entity.workout.Parameter
import com.example.count_out.entity.workout.Round
import com.example.count_out.entity.workout.speech.SpeechKit

data class RoundImpl(
    override val idRound: Long,
    override val trainingId: Long,
    override val speechId: Long,
    override val roundType: RoundType,
    override val speech: SpeechKit?,
    override val exercise: List<Exercise>,
    override val amount: Int,
    override val duration: Parameter
): Round
