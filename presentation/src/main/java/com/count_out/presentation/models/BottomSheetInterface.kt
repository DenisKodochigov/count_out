package com.count_out.presentation.models

import com.count_out.domain.entity.workout.Element
import com.count_out.domain.entity.workout.SpeechKit

interface BottomSheetInterface {
    var item: Element?
    val listSpeech: List<SpeechKit>
    val nameSection: String
    var onConfirmation: (Element, Element?) -> Unit
    var onDismissSpeech: () -> Unit
}