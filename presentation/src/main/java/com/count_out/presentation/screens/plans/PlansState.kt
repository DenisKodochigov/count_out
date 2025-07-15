package com.count_out.presentation.screens.plans

import com.count_out.domain.entity.workout.Training
import com.count_out.presentation.screens.prime.DataState
import com.count_out.presentation.screens.prime.Event

data class PlansState(
    val trainings: List<Training> = emptyList(),
    var selectedId: Long? = null,
    override val event:(Event) -> Unit,
): DataState