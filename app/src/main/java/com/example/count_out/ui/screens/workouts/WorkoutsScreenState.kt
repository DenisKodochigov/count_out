package com.example.count_out.ui.screens.workouts

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.count_out.entity.no_use.Workout

data class WorkoutsScreenState(
    val workouts: MutableState<List<Workout>> = mutableStateOf(emptyList()),
    val enteredName: MutableState<String> = mutableStateOf(""),
    var changeNameWorkout: (Workout) -> Unit = {},
    var editWorkout: (Workout) -> Unit = {},
    var deleteWorkout: (Long) -> Unit = {},
    var onAddClick: (String) -> Unit = {},
    var onDismiss: () -> Unit = {},
    var onSelectItem: (Workout) -> Unit = {},
    var onOtherAction: (Workout) -> Unit = {},
    var onSelect: (Workout) -> Unit = {},
    var onClickWorkout: (Long) ->Unit = {},
    var triggerRunOnClickFAB: MutableState<Boolean> = mutableStateOf(false),
    var idImage: Int = 0,
    var screenTextHeader: String = "",
)