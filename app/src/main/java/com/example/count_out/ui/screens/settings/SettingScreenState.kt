package com.example.count_out.ui.screens.settings

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.count_out.entity.Activity

data class SettingScreenState(
//    val workouts: MutableState<List<Workout>> = mutableStateOf(emptyList()),
//    val enteredName: MutableState<String> = mutableStateOf(""),
//    var changeNameWorkout: (Workout) -> Unit = {},
//    var editWorkout: (Workout) -> Unit = {},
//    var deleteWorkout: (Long) -> Unit = {},
//    var onAddClick: (String) -> Unit = {},
//    var onDismiss: () -> Unit = {},
//    var onClickWorkout: (Long) ->Unit = {},
//    var triggerRunOnClickFAB: MutableState<Boolean> = mutableStateOf(false),
//    var idImage: Int = 0,
//    var screenTextHeader: String = "",
    val collapsingActivity: MutableState<Boolean> = mutableStateOf(false),
    val activities: MutableState<List<Activity>> = mutableStateOf(emptyList()),
    var onClickWorkout: (Long) ->Unit = {},
)