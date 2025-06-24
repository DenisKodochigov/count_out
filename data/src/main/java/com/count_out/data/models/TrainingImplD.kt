package com.count_out.data.models

import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.entity.workout.Round
import com.count_out.domain.entity.workout.SpeechKit
import com.count_out.domain.entity.workout.Training

data class TrainingImplD(
    override val idTraining: Long = 0L,
    override val name: String = "",
    override val amountActivity: Int = 0,
    override val rounds: List<Round> = emptyList(),
    override val rings: List<Ring> = emptyList(),
    override val isSelected: Boolean = false,
    override var speechId: Long = 0L,
    override var speech: SpeechKit? = null,
): Training{
    constructor(tr: Training): this(
        idTraining = tr.idTraining,
        name = tr.name,
        amountActivity = tr.amountActivity,
        rounds= tr.rounds,
        rings = tr.rings,
        isSelected = tr.isSelected,
        speechId = tr.speechId,
        speech = tr.speech
    )
}
