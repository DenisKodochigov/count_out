package com.example.count_out.ui.screens.training

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import com.example.count_out.data.room.tables.TrainingDB
import com.example.count_out.entity.Round
import com.example.count_out.entity.Training
import com.example.count_out.entity.no_use.Workout

data class TrainingScreenState(
    @Stable var trainingId: Long = 0,
    val training: MutableState<Training> = mutableStateOf(TrainingDB()),
    val rounds: MutableState<List<Round>> = mutableStateOf(emptyList()),
    val amountExerciseRounds: MutableState<Int> = mutableIntStateOf(0),
    val enteredName: MutableState<String> = mutableStateOf(""),
    val workUpCollapsing: MutableState<Boolean> = mutableStateOf(false),
    val workOutCollapsing: MutableState<Boolean> = mutableStateOf(false),
    val workDownCollapsing: MutableState<Boolean> = mutableStateOf(false),
    val durationRound: MutableState<Double> = mutableDoubleStateOf(0.0),
    @Stable var changeNameTraining: (Workout) -> Unit = {},
    @Stable var onSpeechTraining: (Long) -> Unit = {},
    @Stable var onDeleteTraining: (Long) -> Unit = {},
    @Stable var onSpeechRound: (Long) -> Unit = {},
    @Stable var onDismissSpeechBSTraining: () -> Unit = {},
    @Stable var onDismissSpeechBSExercise: () -> Unit = {},
    @Stable var onAddExercise: (String) -> Unit = {},
    @Stable var onClickExercise: (Long) -> Unit = {},
    @Stable var onSpeechExercise: (Long) -> Unit = {},
    @Stable var onCopyExercise: (Long) -> Unit = {},
    @Stable var onDeleteExercise: (Long) -> Unit = {},
    @Stable var onSave: (Training) -> Unit = {},
    @Stable var onClickWorkout: (Long) ->Unit = {},
    @Stable var screenTextHeader: String = "",
)