package com.count_out.presentation.view_element.bottom_sheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Part
import com.count_out.domain.entity.workout.Plan
import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.entity.workout.Set
import com.count_out.domain.entity.workout.SpeechKit
import com.count_out.presentation.R
import com.count_out.presentation.models.BottomSheetInterface
import com.count_out.presentation.models.Dimen
import com.count_out.presentation.models.TypeKeyboard
import com.count_out.presentation.screens.plan.PlanEvent
import com.count_out.presentation.screens.plan.PlanEvent.ShowBS
import com.count_out.presentation.screens.plan.PlanState
import com.count_out.presentation.view_element.ButtonConfirm
import com.count_out.presentation.view_element.ModalBottomSheetApp
import com.count_out.presentation.view_element.TextApp
import com.count_out.presentation.view_element.TextFieldApp

@Composable fun ShowBottomSheetSpeech(dataState: PlanState, showBS: Boolean, item: Domain?){
    if (showBS && dataState.item == item) {
        dataState.nameSection = nameSection(item)
        dataState.onDismiss = { dataState.event(ShowBS(dataState.showBS.copy(domain = item)))}
        dataState.onConfirmation = { speechKit ->
            if (speechKit is SpeechKit) {
                dataState.event(PlanEvent.UpdateSpeech(speechKit.beforeStart))
                dataState.event(PlanEvent.UpdateSpeech(speechKit.afterStart))
                dataState.event(PlanEvent.UpdateSpeech(speechKit.beforeEnd))
                dataState.event(PlanEvent.UpdateSpeech(speechKit.afterEnd))
                dataState.event(ShowBS(dataState.showBS.copy(domain = item)))
            }
        }
        BottomSheetSpeech(dataState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable fun BottomSheetSpeech(itemSpeech: BottomSheetInterface) {
    val uiState by remember{ mutableStateOf( bottomSheetStateNew(itemSpeech)) }
    val sheetState = rememberModalBottomSheetState( skipPartiallyExpanded = true )
    ModalBottomSheetApp(
        onDismissRequest = { uiState.onDismiss() },
        modifier = Modifier.padding(horizontal = Dimen.bsPaddingHor1),
        shape = shapes.small,
        sheetState = sheetState,
        content = { BottomSheetSpeechContent(uiState) }
    )
}
fun bottomSheetStateNew(itemSpeech: BottomSheetInterface): BottomSheetState {
    val speechKit = when (itemSpeech.item) {
        is Plan -> (itemSpeech.item as Plan).speechKit
        is Part -> (itemSpeech.item as Part).speechKit
        is Ring -> (itemSpeech.item as Ring).speechKit
        is Exercise -> (itemSpeech.item as Exercise).speechKit
        is Set -> (itemSpeech.item as Set).speechKit
        else -> null
    }
    return BottomSheetState(
        enteredBeforeStart = mutableStateOf( speechKit?.beforeStart?.message ?: ""),
        enteredBeforeEnd = mutableStateOf( speechKit?.afterStart?.message ?: ""),
        enteredAfterStart = mutableStateOf( speechKit?.beforeEnd?.message ?: ""),
        enteredAfterEnd = mutableStateOf( speechKit?.afterEnd?.message ?: ""),
        speechKit= speechKit,
        nameSection = itemSpeech.nameSection,
        item = itemSpeech.item,
        onConfirmation = itemSpeech.onConfirmation,
        onDismiss = itemSpeech.onDismiss,
    )
}

fun itemLogo(item: Domain): String {
    return when (item) {
        is Exercise -> "Exercise ${item.idExercise}"
        is Plan -> "Plan ${item.idPlan}"
        is Part -> "Part ${item.idPart}"
        is Ring -> "Ring ${item.idRing}"
        is Set -> "Set ${item.idSet}"
        else -> ""
    }
}
@Composable fun BottomSheetSpeechContent(uiState: BottomSheetState) {
    Column( horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(6.dp)) {
//        SelectOtherSpeech(uiState)
        Spacer(Modifier.height(Dimen.bsSpacerHeight))
        FieldTextForSpeech( enterValue = uiState.enteredBeforeStart,
            nameSection = stringResource(id = R.string.message_before_start) + " " + uiState.nameSection)
        Spacer(Modifier.height(Dimen.bsSpacerHeight))
        FieldTextForSpeech( enterValue = uiState.enteredAfterStart,
            nameSection = stringResource(id = R.string.message_after_start) + " " + uiState.nameSection)
        Spacer(Modifier.height(Dimen.bsSpacerHeight))
        FieldTextForSpeech( enterValue = uiState.enteredBeforeEnd,
            nameSection = stringResource(id = R.string.message_before_end) + " " + uiState.nameSection)
        Spacer(Modifier.height(Dimen.bsSpacerHeight))
        FieldTextForSpeech(
            enterValue = uiState.enteredAfterEnd,
            nameSection = stringResource(id = R.string.message_after_end) + " " + uiState.nameSection )
        Spacer(Modifier.height(Dimen.bsConfirmationButtonTopHeight))
        ButtonOK(uiState)
        Spacer(Modifier.height(Dimen.bsSpacerBottomHeight))
    }
}
@Composable fun FieldTextForSpeech(enterValue: MutableState<String>, nameSection: String){
    Column(modifier = Modifier.padding(horizontal = 12.dp)) {
        TextFieldApp(
            modifier = Modifier.fillMaxWidth(),
            typeKeyboard = TypeKeyboard.TEXT,
            textStyle = MaterialTheme.typography.titleLarge,
            contentAlignment = Alignment.CenterStart,
            placeholder = enterValue.value,
            showLine = true,
            maxLines = 3,
            edit = true,
            onChangeValue = { enterValue.value = it}
        )
        TextApp(text = nameSection, modifier = Modifier.padding(start = 6.dp),
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Light))
    }
}
@Composable fun ButtonOK(uiState: BottomSheetState) {
    ButtonConfirm( onConfirm = {
        uiState.speechKit?.let {
            uiState.onConfirmation( SpeechKit.fill( listOf(
                it.beforeStart.copy(message = uiState.enteredBeforeStart.value),
                it.beforeEnd.copy(message = uiState.enteredBeforeEnd.value),
                it.afterStart.copy(message = uiState.enteredAfterStart.value),
                it.afterEnd.copy(message = uiState.enteredAfterEnd.value) )))
        }
    })
}

@Preview(showBackground = true)
@Composable fun BottomSheetSpeechContentPreview() {
    BottomSheetSpeechContent(BottomSheetState())
}
@Composable fun nameSection(item: Domain? = null): String {
    return when (item) {
        is Plan -> stringResource(id = R.string.training,)
        is Part -> stringResource(id = R.string.part,)
        is Ring -> stringResource(id = R.string.ring,)
        is Exercise -> stringResource(id = R.string.exercise2,)
        is Set -> stringResource(id = R.string.set,)
        else -> ""
    }
}