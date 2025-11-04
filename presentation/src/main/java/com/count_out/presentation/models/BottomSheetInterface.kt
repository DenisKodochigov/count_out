package com.count_out.presentation.models

import com.count_out.domain.entity.workout.Domain

interface BottomSheetInterface {
    var item: Domain?
    val nameSection: String
    var onConfirmation: (Domain) -> Unit
    var onDismiss: () -> Unit
}