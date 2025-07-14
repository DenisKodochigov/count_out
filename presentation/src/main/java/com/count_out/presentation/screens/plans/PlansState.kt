package com.count_out.presentation.screens.plans

import com.count_out.domain.entity.workout.Training
import com.count_out.presentation.screens.prime.Action
import com.count_out.presentation.screens.prime.DataState

data class PlansState(
    val trainings: List<Training> = emptyList(),
    var selectedId: Long? = null,
    override val event: Action,
): DataState