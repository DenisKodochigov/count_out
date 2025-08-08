package com.count_out.presentation.screens.training

import androidx.compose.runtime.Stable
import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.entity.workout.Collapsing
import com.count_out.domain.entity.workout.Element
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Set
import com.count_out.domain.entity.workout.ShowBottomSheet
import com.count_out.domain.entity.workout.SpeechKit
import com.count_out.domain.entity.workout.Training
import com.count_out.presentation.models.BottomSheetInterface
import com.count_out.presentation.screens.prime.DataState
import com.count_out.presentation.screens.prime.Event

data class TrainingState (
    val training: Training? = null,
//    val enteredName: String = "",
    val showBS: ShowBottomSheet = ShowBottomSheet(),
    val collapsing: Collapsing = Collapsing(),

    val nameTraining: String = "",
    val roundId: Long = 0,
    @Stable var exercise: Exercise? = null,
    @Stable var set: Set? = null,
    val activities: List<Activity> = emptyList(),

    var onBaskScreen: () ->Unit = {},
    var screenTextHeader: String = "",
    override var listSpeech: List<SpeechKit> = emptyList(),
    override var nameSection: String = "",
    override var item: Element? = null,
    override var onConfirmation: (Element, Element?) -> Unit = { _, _ ->},
    override var onDismissSpeech: () -> Unit = {},
    override val event: (Event) -> Unit,
): BottomSheetInterface, DataState
