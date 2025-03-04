package com.count_out.data.models

import com.count_out.entity.entity.workout.Parameter
import com.count_out.entity.enums.Units

data class ParameterImpl(
    override val value: Double,
    override val unit: Units
): Parameter