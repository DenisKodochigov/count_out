package com.count_out.presentation.models

import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.entity.workout.Round
import com.count_out.domain.entity.workout.SpeechKit
import com.count_out.domain.entity.workout.Training

data class TrainingImpl(
    override val idTraining: Long = 0L,
    override val name: String = "",
    override val amountActivity: Int = 0,
    override val rounds: List<Round> = emptyList(),
    override val rings: List<Ring> = emptyList(),
    override val isSelected: Boolean = false,
    override var speechId: Long = 0L,
    override var speech: SpeechKit? = null,
): Training
