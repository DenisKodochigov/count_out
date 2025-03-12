package com.count_out.presentation.models

import com.count_out.domain.entity.workout.SpeechKit

interface BottomSheetInterface {
    var item: Any?
    val listSpeech: List<SpeechKit>
    val nameSection: String
    var onConfirmationSpeech: (SpeechKit, Any?) -> Unit
    var onDismissSpeech: () -> Unit
}