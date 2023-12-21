package com.example.count_out.entity

interface BottomSheetInterface {
    var item: Any?
    val listSpeech: List<Speech>
    val nameSection: String
    var onConfirmationSpeech: (Speech, Any?) -> Unit
    var onDismissSpeech: () -> Unit
}