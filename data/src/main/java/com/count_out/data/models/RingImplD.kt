package com.count_out.data.models

import com.count_out.domain.entity.enums.Units
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Parameter
import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.entity.workout.SpeechKit

data class RingImplD(
    override val idRing: Long = 0L,
    override var name: String = "",
    override val countRing: Int = 0,
    override val trainingId: Long = 0L,
    override val speechId: Long = 0L,
    override val speech: SpeechKit? = null,
    override val exercise: List<Exercise> = emptyList(),
    override val amount: Int = 0,
    override val duration: Parameter = ParameterImpl(value = 0.0, unit = Units.M),
): Ring{
    constructor(ring: Ring) : this(
        idRing = ring.idRing,
        name = ring.name,
        countRing = ring.countRing,
        trainingId = ring.trainingId,
        speechId = ring.speechId,
        speech = ring.speech,
        exercise = ring.exercise,
        duration = ring.duration,
        amount = ring.amount,
    )}
