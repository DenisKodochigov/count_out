package com.example.count_out.ui.modules

import com.example.count_out.entity.speech.SpeechKit

interface BottomSheetInterface {
    var item: Any?
    val listSpeech: List<SpeechKit>
    val nameSection: String
    var onConfirmationSpeech: (SpeechKit, Any?) -> Unit
    var onDismissSpeech: () -> Unit
}