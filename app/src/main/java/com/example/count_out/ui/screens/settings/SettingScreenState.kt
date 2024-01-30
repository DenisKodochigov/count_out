package com.example.count_out.ui.screens.settings

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import com.example.count_out.data.room.tables.ActivityDB
import com.example.count_out.entity.Activity

data class SettingScreenState(
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
    val showBottomSheetAddActivity: MutableState<Boolean> = mutableStateOf(false),
    val collapsingActivity: MutableState<Boolean> = mutableStateOf(false),
    val activities: List<Activity> = emptyList(),
    val activity: MutableState<Activity> = mutableStateOf(ActivityDB()),
    @Stable val onSetColorActivity: (Long, Int) -> Unit = { _, _ ->},
    @Stable val onAddActivity: (Activity) ->Unit = {},
    @Stable val onUpdateActivity: (Activity) ->Unit = {},
    @Stable val onDeleteActivity: (Long) ->Unit = {},

    @Stable var onDismissAddActivity: () -> Unit = {},
    @Stable var onConfirmAddActivity: (Activity) -> Unit = {},
)