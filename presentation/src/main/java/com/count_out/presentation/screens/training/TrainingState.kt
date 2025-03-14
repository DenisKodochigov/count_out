package com.count_out.presentation.screens.training

import androidx.compose.runtime.Stable
import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Set
import com.count_out.domain.entity.workout.SpeechKit
import com.count_out.domain.entity.workout.Training
import com.count_out.presentation.models.BottomSheetInterface

data class TrainingState (
    val training: Training? = null,
//    val enteredName: String = "",
    val showSpeechTraining: Boolean = false,
    val showSpeechWorkUp: Boolean = false,
    val showSpeechWorkOut: Boolean = false,
    val showSpeechWorkDown: Boolean = false,
    val showSpeechExercise: Boolean = false,
    val showSpeechSet: Boolean = false,
    val showSelectActivity: Boolean = false,

    val nameTraining: String = "",
    val roundId: Long = 0,
    @Stable var exercise: Exercise? = null,
    @Stable var set: Set? = null,
    val activities: List<Activity> = emptyList(),

    val workUpCollapsing: Boolean = true,
    val workOutCollapsing: Boolean = true,
    val workDownCollapsing: Boolean = true,
    val listCollapsingSet: List<Long> = emptyList(),
    val listCollapsingExercise: List<Long> = emptyList(),
//    @Stable var onDismissSelectActivity: () -> Unit = {},

    @Stable var onBaskScreen: () ->Unit = {},
    @Stable var screenTextHeader: String = "",
    @Stable override var listSpeech: List<SpeechKit> = emptyList(),
    @Stable override var nameSection: String = "",
    @Stable override var onConfirmationSpeech: (SpeechKit, Any?) -> Unit = { _, _ ->},
    @Stable override var item: Any? = null,
    @Stable override var onDismissSpeech: () -> Unit = {},
): BottomSheetInterface