package com.count_out.presentation.screens.trainings

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.count_out.domain.entity.workout.Training

data class TrainingsState(
    val trainings: List<Training> = emptyList(),
    val selectedId: MutableState<Long?> = mutableStateOf(0L),
)