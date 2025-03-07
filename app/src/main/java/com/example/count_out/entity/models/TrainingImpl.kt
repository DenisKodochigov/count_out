package com.example.count_out.entity.models

import com.example.count_out.entity.workout.Round
import com.example.count_out.entity.workout.Training
import com.example.count_out.entity.workout.speech.SpeechKit

data class TrainingImpl(
    override val idTraining: Long,
    override val name: String,
    override val amountActivity: Int,
    override val rounds: List<Round>,
    override val isSelected: Boolean,
    override var speechId: Long,
    override var speech: SpeechKit?
): Training
