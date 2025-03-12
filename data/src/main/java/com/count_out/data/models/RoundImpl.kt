package com.count_out.data.models

import com.count_out.domain.entity.enums.RoundType
import com.count_out.domain.entity.enums.Units
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Parameter
import com.count_out.domain.entity.workout.Round
import com.count_out.domain.entity.workout.SpeechKit

data class RoundImpl(
    override val idRound: Long = 0L,
    override val trainingId: Long = 0L,
    override val speechId: Long = 0L,
    override val roundType: RoundType,
    override val speech: SpeechKit? = null,
    override val exercise: List<Exercise> = emptyList(),
    override val amount: Int = 0,
    override val duration: Parameter = ParameterImpl(0.0, Units.M)
): Round