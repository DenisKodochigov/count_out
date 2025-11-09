package com.count_out.presentation.models

import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.entity.workout.Domain
import com.count_out.presentation.screens.prime.DataState
import com.count_out.presentation.screens.prime.Event

interface BottomSheetInterface: DataState {
    var item: Domain?
    var activities: List<Activity>
    var nameSection: String
    var onConfirmation: (Domain) -> Unit
    var onDismiss: () -> Unit
    override val event: (Event) -> Unit
}