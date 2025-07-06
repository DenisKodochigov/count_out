package com.count_out.presentation.screens.plans

import com.count_out.domain.entity.workout.Training

data class PlansState(
    val trainings: List<Training> = emptyList(),
    var selectedId: Long? = null,
)