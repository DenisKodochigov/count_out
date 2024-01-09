package com.example.count_out.ui.screens.exercise

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import com.example.count_out.data.room.tables.ExerciseDB
import com.example.count_out.entity.Activity
import com.example.count_out.entity.BottomSheetInterface
import com.example.count_out.entity.Exercise
import com.example.count_out.entity.Set
import com.example.count_out.entity.Speech
import javax.inject.Singleton

@Singleton
data class ExerciseScreenState(
    @Stable val nameTraining: String = "",
    @Stable val nameRound: String = "",
    @Stable val roundId: Long = 0,
    @Stable val exercise: Exercise = ExerciseDB(),
    @Stable val activities: List<Activity> = emptyList(),

    @Stable val listCollapsingSet: MutableState<List<Long>> = mutableStateOf(emptyList()),
    @Stable var onAddUpdateSet: (Long, Set) -> Unit = {_,_ ->},
    @Stable val showBottomSheetSpeech: MutableState<Boolean> = mutableStateOf(false),
    @Stable val showBottomSheetSelectActivity: MutableState<Boolean> = mutableStateOf(false),
    @Stable var onDismissSelectActivity: () -> Unit = {},
    @Stable var onSelectActivity: (Long, Long) -> Unit = {_,_ ->},
    @Stable var onSetColorActivity: (Long, Int) -> Unit = {_,_ ->},
    @Stable var doChangeActivity: (Activity) -> Unit = {},

    @Stable val enteredDuration: MutableState<String> = mutableStateOf(""),
    @Stable val enteredWeight: MutableState<String> = mutableStateOf(""),
    @Stable val enteredDistance: MutableState<String> = mutableStateOf(""),
    @Stable val enteredIntensity: MutableState<String> = mutableStateOf(""),
    @Stable val enteredReps: MutableState<String> = mutableStateOf(""),
    @Stable val enteredTimeRest: MutableState<String> = mutableStateOf(""),
    @Stable val enteredIntervalDown: MutableState<String> = mutableStateOf(""),
    @Stable val enteredIntervalReps: MutableState<String> = mutableStateOf(""),
    @Stable val enteredName: MutableState<String> = mutableStateOf(""),
    @Stable val enteredGroupCount: MutableState<String> = mutableStateOf(""),

    @Stable override var listSpeech: List<Speech> = emptyList(),
    @Stable override var item: Any? =null,
    @Stable override var nameSection: String = "",
    @Stable override var onConfirmationSpeech: (Speech, Any?) -> Unit = {_,_->},
    @Stable override var onDismissSpeech: () -> Unit = {},
): BottomSheetInterface