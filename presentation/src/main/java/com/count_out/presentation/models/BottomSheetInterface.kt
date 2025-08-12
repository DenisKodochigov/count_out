package com.count_out.presentation.models

import com.count_out.domain.entity.workout.Element

interface BottomSheetInterface {
    var item: Element?
    val nameSection: String
    var onConfirmation: (Element, Element?) -> Unit
    var onDismiss: () -> Unit
}