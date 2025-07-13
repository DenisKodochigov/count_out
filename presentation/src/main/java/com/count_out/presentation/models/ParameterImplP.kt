package com.count_out.presentation.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.count_out.domain.entity.enums.Units
import com.count_out.domain.entity.workout.Parameter

data class ParameterImplP(
    override val value: Double,
    override val unit: Units
): Parameter {
    constructor(from: Parameter): this(
        value = from.value,
        unit = from.unit
    )
    @Composable
    fun print(): String = if(value > 0) "(${value } ${stringResource(unit.id)})" else "-"
}
