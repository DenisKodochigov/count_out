package com.example.data.entity

import com.example.domain.entity.Round
import com.example.domain.entity.SpeechKit
import com.example.domain.entity.Training

data class TrainingImpl(
    override val idTraining: Long,
    override val name: String,
    override val amountActivity: Int,
    override val rounds: List<Round>,
    override val isSelected: Boolean,
    override var speechId: Long,
    override var speech: SpeechKit?,
): Training