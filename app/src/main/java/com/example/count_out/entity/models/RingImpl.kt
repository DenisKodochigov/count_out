package com.example.count_out.entity.models

import com.example.count_out.entity.workout.Exercise
import com.example.count_out.entity.workout.Parameter
import com.example.count_out.entity.workout.Ring
import com.example.count_out.entity.workout.speech.SpeechKit

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
