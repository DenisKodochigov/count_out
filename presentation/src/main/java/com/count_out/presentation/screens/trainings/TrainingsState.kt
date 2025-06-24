package com.count_out.presentation.screens.trainings

import com.count_out.domain.entity.workout.Training

data class TrainingsState(
    val trainings: List<Training> = emptyList(),
    var selectedId: Long? = null,
)