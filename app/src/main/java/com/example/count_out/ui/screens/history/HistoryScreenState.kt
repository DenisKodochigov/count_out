package com.example.count_out.ui.screens.history

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import com.example.count_out.entity.TrainingStatistics
import com.example.count_out.entity.UnitTime

data class HistoryScreenState (
    val unitTime: MutableState<UnitTime?> = mutableStateOf(null),
    val listTraining: MutableState<TrainingStatistics?> = mutableStateOf(null),

    @Stable val getTraining: (String) ->Unit = {},
    @Stable val getTrainings: (String) ->Unit = {},
)