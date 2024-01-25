package com.example.count_out.ui.screens.set

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import com.example.count_out.entity.BottomSheetInterface
import com.example.count_out.entity.Speech
import com.example.count_out.entity.no_use.Workout

data class SetScreenState(
    val workouts: MutableState<List<Workout>> = mutableStateOf(emptyList()),
    val enteredName: MutableState<String> = mutableStateOf(""),
    var changeNameWorkout: (Workout) -> Unit = {},
    var editWorkout: (Workout) -> Unit = {},
    var deleteWorkout: (Long) -> Unit = {},
    var onAddClick: (String) -> Unit = {},
    var onDismiss: () -> Unit = {},
    var onClickWorkout: (Long) ->Unit = {},
    var triggerRunOnClickFAB: MutableState<Boolean> = mutableStateOf(false),
    var idImage: Int = 0,
    var screenTextHeader: String = "",
    @Stable override var listSpeech: List<Speech> = emptyList(),
    @Stable override var nameSection: String = "",
    @Stable override var onConfirmationSpeech: ( Speech, Any?) -> Unit = { _,_->},
    @Stable override var item: Any? =null,
    @Stable override var onDismissSpeech: () -> Unit = {},
): BottomSheetInterface