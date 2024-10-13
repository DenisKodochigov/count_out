package com.example.count_out.ui.bottomsheet

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import com.example.count_out.data.room.tables.SpeechKitDB
import com.example.count_out.entity.ui.BottomSheetInterface
import com.example.count_out.entity.speech.SpeechKit

data class BottomSheetState(
    val elementSpeech: MutableState<String> = mutableStateOf(""),
    val enteredBeforeStart: MutableState<String> = mutableStateOf(""),
    val enteredBeforeEnd: MutableState<String> = mutableStateOf(""),
    val enteredAfterStart: MutableState<String> = mutableStateOf(""),
    val enteredAfterEnd: MutableState<String> = mutableStateOf(""),
    @Stable var speechKit: SpeechKit = SpeechKitDB(),
    @Stable override var item: Any? =null,
    @Stable override var listSpeech: List<SpeechKit> = emptyList(),
    @Stable override var nameSection: String = "",
    @Stable override var onConfirmationSpeech: (SpeechKit, Any?) -> Unit = { _, _->},
    @Stable override var onDismissSpeech: () -> Unit = {},
): BottomSheetInterface
