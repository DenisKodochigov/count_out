package com.count_out.presentation.models

import com.count_out.domain.entity.router.DeviceBle
import com.count_out.domain.entity.workout.Domain
import com.count_out.presentation.screens.prime.DataState
import com.count_out.presentation.screens.prime.Event

interface BottomSheetInterface: DataState {
    var item: Domain?
    var list: List<Domain>
    var nameSection: String
    val devicesUI: List<DeviceBle>
    var onConfirmation: (Domain) -> Unit
    var onDismiss: () -> Unit
    override val event: (Event) -> Unit
}