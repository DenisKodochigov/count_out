package com.count_out.presentation.screens.plan

import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.entity.workout.Collapsing
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.Plan
import com.count_out.domain.entity.workout.ShowBottomSheet
import com.count_out.presentation.models.BottomSheetInterface
import com.count_out.presentation.screens.prime.DataState
import com.count_out.presentation.screens.prime.Event

data class PlanState (
    val plan: Plan? = null,
    val showBS: ShowBottomSheet = ShowBottomSheet(),
    val collapsing: Collapsing = Collapsing(),
    val activities: List<Activity> = emptyList(),

    override var nameSection: String = "",
    override var item: Domain? = null,
    override var onConfirmation: (Domain, Domain?) -> Unit = { _, _ ->},
    override var onDismiss: () -> Unit = {},
    override val event: (Event) -> Unit,
): BottomSheetInterface, DataState
