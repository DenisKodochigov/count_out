package com.example.data.entity

import com.example.domain.entity.Exercise
import com.example.domain.entity.Ring
import com.example.domain.entity.SpeechKit

data class RingImpl(
    override val idRing: Long,
    override var name: String,
    override val countRing: Int,
    override val trainingId: Long,
    override val speechId: Long,
    override val speech: SpeechKit?,
    override val exercise: List<Exercise>,
): Ring
