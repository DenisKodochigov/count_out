package com.example.data.entity

import com.example.domain.entity.Exercise
import com.example.domain.entity.Round
import com.example.domain.entity.SpeechKit
import com.example.domain.entity.enums.RoundType

data class RoundImpl(
    override val idRound: Long,
    override val trainingId: Long,
    override val speechId: Long,
    override val roundType: RoundType,
    override val speech: SpeechKit?,
    override val exercise: List<Exercise>
): Round