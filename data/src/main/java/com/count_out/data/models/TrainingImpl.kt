package com.count_out.data.models

import com.count_out.domain.entity.workout.Round
import com.count_out.domain.entity.workout.SpeechKit
import com.count_out.domain.entity.workout.Training

data class TrainingImpl(
    override val idTraining: Long,
    override val name: String,
    override val amountActivity: Int,
    override val rounds: List<Round>,
    override val isSelected: Boolean,
    override var speechId: Long,
    override var speech: SpeechKit?,
): Training