package com.count_out.data.entity

import com.count_out.domain.entity.Exercise
import com.count_out.domain.entity.Parameter
import com.count_out.domain.entity.Ring
import com.count_out.domain.entity.SpeechKit

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
