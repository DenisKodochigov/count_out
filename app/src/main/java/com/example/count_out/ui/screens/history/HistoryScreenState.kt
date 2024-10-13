package com.example.count_out.ui.screens.history

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import com.example.count_out.entity.UnitTime
import com.example.count_out.entity.workout.Workout
import java.time.LocalDate

data class HistoryScreenState (
    val unitTime: MutableState<UnitTime?> = mutableStateOf(null),
    val listTraining: MutableState<Workout?> = mutableStateOf(null),
    val currentDay: Int = LocalDate.now().dayOfYear,
    val currentMonth: Int = LocalDate.now().monthValue,
    val currentYear: Int = LocalDate.now().year,

    @Stable val getTraining: (String) ->Unit = {},
    @Stable val getTrainings: (String) ->Unit = {},
    @Stable val onClickDay: (String) ->Unit = {},
)