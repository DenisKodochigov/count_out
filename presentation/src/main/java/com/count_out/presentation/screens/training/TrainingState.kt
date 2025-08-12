package com.count_out.presentation.screens.training

import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.entity.workout.Collapsing
import com.count_out.domain.entity.workout.Element
import com.count_out.domain.entity.workout.ShowBottomSheet
import com.count_out.domain.entity.workout.Training
import com.count_out.presentation.models.BottomSheetInterface
import com.count_out.presentation.screens.prime.DataState
import com.count_out.presentation.screens.prime.Event

data class TrainingState (
    val training: Training? = null,
    val showBS: ShowBottomSheet = ShowBottomSheet(),
    val collapsing: Collapsing = Collapsing(),
    val activities: List<Activity> = emptyList(),

    override var nameSection: String = "",
    override var item: Element? = null,
    override var onConfirmation: (Element, Element?) -> Unit = { _, _ ->},
    override var onDismiss: () -> Unit = {},
    override val event: (Event) -> Unit,
): BottomSheetInterface, DataState
