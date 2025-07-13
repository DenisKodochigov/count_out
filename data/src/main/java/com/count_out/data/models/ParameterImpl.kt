package com.count_out.data.models

import com.count_out.domain.entity.enums.Units
import com.count_out.domain.entity.workout.Parameter

data class ParameterImpl(
    override val value: Double,
    override val unit: Units
): Parameter