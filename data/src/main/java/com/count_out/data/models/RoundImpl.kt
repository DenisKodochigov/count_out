package com.count_out.data.models

import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Parameter
import com.count_out.domain.entity.workout.Round
import com.count_out.domain.entity.workout.SpeechKit
import com.count_out.domain.entity.enums.RoundType

data class RoundImpl(
    override val idRound: Long,
    override val trainingId: Long,
    override val speechId: Long,
    override val roundType: RoundType,
    override val speech: SpeechKit?,
    override val exercise: List<Exercise>,
    override val amount: Int,
    override val duration: Parameter
): Round