package com.example.count_out.ui.screens.training

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import com.example.count_out.entity.workout.Activity
import com.example.count_out.entity.workout.Exercise
import com.example.count_out.entity.workout.Set
import com.example.count_out.entity.workout.Training
import com.example.count_out.entity.workout.speech.SpeechKit
import com.example.count_out.ui.models.BottomSheetInterface

data class TrainingState (
    val training: Training,
    val enteredName: MutableState<String> = mutableStateOf(""),
    val showSpeechTraining: MutableState<Boolean> = mutableStateOf(false),
    val showSpeechWorkUp: MutableState<Boolean> = mutableStateOf(false),
    val showSpeechWorkOut: MutableState<Boolean> = mutableStateOf(false),
    val showSpeechWorkDown: MutableState<Boolean> = mutableStateOf(false),
    val showSpeechExercise: MutableState<Boolean> = mutableStateOf(false),
    val showSpeechSet: MutableState<Boolean> = mutableStateOf(false),
    val workUpCollapsing: MutableState<Boolean> = mutableStateOf(true),
    val workOutCollapsing: MutableState<Boolean> = mutableStateOf(true),
    val workDownCollapsing: MutableState<Boolean> = mutableStateOf(true),

    val nameTraining: String = "",
    val roundId: Long = 0,
    @Stable var exercise: Exercise,
    @Stable var set: Set,
    val activities: List<Activity> = emptyList(),

    @Stable val listCollapsingSet: MutableState<List<Long>> = mutableStateOf(emptyList()),
    @Stable val listCollapsingExercise: MutableState<List<Long>> = mutableStateOf(emptyList()),
    @Stable val showBottomSheetSpeech: MutableState<Boolean> = mutableStateOf(false),
    @Stable val showBottomSheetSelectActivity: MutableState<Boolean> = mutableStateOf(false),
    @Stable var onDismissSelectActivity: () -> Unit = {},

    @Stable var onBaskScreen: () ->Unit = {},
    @Stable var screenTextHeader: String = "",
    @Stable override var listSpeech: List<SpeechKit> = emptyList(),
    @Stable override var nameSection: String = "",
    @Stable override var onConfirmationSpeech: (SpeechKit, Any?) -> Unit = { _, _ ->},
    @Stable override var item: Any? = null,
    @Stable override var onDismissSpeech: () -> Unit = {},
): BottomSheetInterface