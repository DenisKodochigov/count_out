package com.count_out.presentation.screens.trainings

import androidx.compose.runtime.MutableState
import com.count_out.domain.entity.workout.Training

data class TrainingsState(
    val trainings: List<Training>,
    val selectedId: MutableState<Long?>,
)