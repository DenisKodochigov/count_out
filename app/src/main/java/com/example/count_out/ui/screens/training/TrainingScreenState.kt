package com.example.count_out.ui.screens.training

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import com.example.count_out.data.room.tables.ExerciseDB
import com.example.count_out.data.room.tables.TrainingDB
import com.example.count_out.entity.Activity
import com.example.count_out.entity.BottomSheetInterface
import com.example.count_out.entity.Exercise
import com.example.count_out.entity.Set
import com.example.count_out.entity.Speech
import com.example.count_out.entity.Training

data class TrainingScreenState(
    val training: Training = TrainingDB(),
    val enteredName: MutableState<String> = mutableStateOf(""),
    val showSpeechTraining: MutableState<Boolean> = mutableStateOf(false),
    val showSpeechWorkUp: MutableState<Boolean> = mutableStateOf(false),
    val showSpeechWorkOut: MutableState<Boolean> = mutableStateOf(false),
    val showSpeechWorkDown: MutableState<Boolean> = mutableStateOf(false),
    val showSpeechExercise: MutableState<Exercise?> = mutableStateOf(null),
    val workUpCollapsing: MutableState<Boolean> = mutableStateOf(true),
    val workOutCollapsing: MutableState<Boolean> = mutableStateOf(true),
    val workDownCollapsing: MutableState<Boolean> = mutableStateOf(true),
    val durationRound: MutableState<Double> = mutableDoubleStateOf(0.0),

    val nameTraining: String = "",
    val nameRound: String = "",
    val roundId: Long = 0,
    val exercise: Exercise = ExerciseDB(),
    val activities: List<Activity> = emptyList(),


    @Stable var changeNameTraining: (Training, String) -> Unit = {_,_ ->},
    @Stable var onSpeechTraining: (Long) -> Unit = {},
    @Stable var onDeleteTraining: (Long) -> Unit = {},
    @Stable var onSpeechRound: (Long) -> Unit = {},
    @Stable var onDismissSpeechBSTraining: () -> Unit = {},
    @Stable var onDismissSpeechBSExercise: () -> Unit = {},
    @Stable var onAddExercise: (Long) -> Unit = { },
    @Stable var onClickExercise: (Long, Long) -> Unit = {_,_ ->},
    @Stable var onSpeechExercise: (Long) -> Unit = {},
    @Stable var onCopyExercise: (Long, Long) -> Unit = {_,_ ->},
    @Stable var onDeleteExercise: (Long, Long) -> Unit = {_,_ ->},

    @Stable val listCollapsingSet: MutableState<List<Long>> = mutableStateOf(emptyList()),
    @Stable var onAddUpdateSet: (Long, Set) -> Unit = { _, _ ->},
    @Stable val showBottomSheetSpeech: MutableState<Boolean> = mutableStateOf(false),
    @Stable val showBottomSheetSelectActivity: MutableState<Boolean> = mutableStateOf(false),
    @Stable var onDismissSelectActivity: () -> Unit = {},
    @Stable var onSelectActivity: (Long, Long) -> Unit = {_,_ ->},
    @Stable var onSetColorActivity: (Long, Int) -> Unit = {_,_ ->},
    @Stable var doChangeActivity: (Activity) -> Unit = {},
    @Stable var onChangeSet: (Set) -> Unit = {},


    @Stable var onSave: (Training) -> Unit = {},
    @Stable var onClickWorkout: (Long) ->Unit = {},
    @Stable var onBaskScreen: () ->Unit = {},
    @Stable var screenTextHeader: String = "",
    @Stable override var listSpeech: List<Speech> = emptyList(),
    @Stable override var nameSection: String = "",
    @Stable override var onConfirmationSpeech: (Speech, Any?) -> Unit = {_,_ ->},
    @Stable override var item: Any? = null,
    @Stable override var onDismissSpeech: () -> Unit = {},
): BottomSheetInterface