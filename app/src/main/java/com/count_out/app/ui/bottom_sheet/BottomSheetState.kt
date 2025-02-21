package com.count_out.app.ui.bottom_sheet

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import com.count_out.app.ui.modules.BottomSheetInterface
import com.count_out.domain.entity.SpeechKit

data class BottomSheetState(
    val elementSpeech: MutableState<String> = mutableStateOf(""),
    val enteredBeforeStart: MutableState<String> = mutableStateOf(""),
    val enteredBeforeEnd: MutableState<String> = mutableStateOf(""),
    val enteredAfterStart: MutableState<String> = mutableStateOf(""),
    val enteredAfterEnd: MutableState<String> = mutableStateOf(""),
    @Stable var speechKit: SpeechKit? = null,
    @Stable override var item: Any? =null,
    @Stable override var listSpeech: List<SpeechKit> = emptyList(),
    @Stable override var nameSection: String = "",
    @Stable override var onConfirmationSpeech: (SpeechKit, Any?) -> Unit = { _, _->},
    @Stable override var onDismissSpeech: () -> Unit = {},
): BottomSheetInterface
