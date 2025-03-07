package com.example.count_out.ui.screens.trainings

import androidx.compose.runtime.MutableState
import com.example.count_out.entity.workout.Training
import com.example.count_out.ui.screens.prime.ScreenState

//import com.count_out.app.entity.workout.Training

data class TrainingsState(
    val trainings: List<Training>,
    val selectedId: MutableState<Long?>,
)