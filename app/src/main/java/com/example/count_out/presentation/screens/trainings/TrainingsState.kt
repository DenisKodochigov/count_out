package com.example.count_out.presentation.screens.trainings

import androidx.compose.runtime.MutableState
import com.example.domain.entity.Training
//import com.example.count_out.entity.workout.Training

data class TrainingsState(
    val trainings: List<Training>,
    val selectedId: MutableState<Long?>,
)