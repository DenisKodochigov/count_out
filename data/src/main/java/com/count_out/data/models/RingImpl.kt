package com.count_out.data.models

import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Parameter
import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.entity.workout.SpeechKit


data class RingImpl(
    override val idRing: Long,
    override var name: String,
    override val countRing: Int,
    override val trainingId: Long,
    override val speechId: Long,
    override val speech: SpeechKit?,
    override val exercise: List<Exercise>,
    override val amount: Int,
    override val duration: Parameter,
): Ring
