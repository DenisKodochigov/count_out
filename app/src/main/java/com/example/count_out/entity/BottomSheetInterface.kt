package com.example.count_out.entity

interface BottomSheetInterface {
    var item: Any?
    val listSpeech: List<SpeechKit>
    val nameSection: String
    var onConfirmationSpeech: (SpeechKit, Any?) -> Unit
    var onDismissSpeech: () -> Unit
}