package com.count_out.presentation.view_element.bottom_sheet

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Part
import com.count_out.domain.entity.workout.Plan
import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.entity.workout.Set
import com.count_out.domain.entity.workout.SpeechKit
import com.count_out.presentation.models.BottomSheetInterface

data class BottomSheetState(
    val elementSpeech: MutableState<String> = mutableStateOf(""),
    val enteredBeforeStart: MutableState<String> = mutableStateOf(""),
    val enteredBeforeEnd: MutableState<String> = mutableStateOf(""),
    val enteredAfterStart: MutableState<String> = mutableStateOf(""),
    val enteredAfterEnd: MutableState<String> = mutableStateOf(""),
    var speechKit: SpeechKit? = null,
    var item: Domain? = null,
    var nameSection: String = "",
    var onConfirmation: (Domain) -> Unit = { },
    var onDismiss: () -> Unit = {},
){
    fun fromPlanState(state: BottomSheetInterface):BottomSheetState {
        val spKit = getSpeech(state.item)
        return BottomSheetState(
            elementSpeech = mutableStateOf(""),
            enteredBeforeStart = mutableStateOf(spKit?.beforeStart?.message ?: ""),
            enteredBeforeEnd = mutableStateOf(spKit?.beforeEnd?.message ?: ""),
            enteredAfterStart = mutableStateOf(spKit?.afterStart?.message ?: ""),
            enteredAfterEnd = mutableStateOf(spKit?.afterEnd?.message ?: ""),
            speechKit = spKit,
            item = state.item,
            nameSection = state.nameSection,
            onConfirmation = state.onConfirmation,
            onDismiss = state.onDismiss,
        )
    }
    fun getSpeech(item: Domain?): SpeechKit? {
       return when (item) {
            is Plan -> item.speechKit
            is Part -> item.speechKit
            is Ring -> item.speechKit
            is Exercise -> item.speechKit
            is Set -> item.speechKit
            else -> null
        }
    }
}
