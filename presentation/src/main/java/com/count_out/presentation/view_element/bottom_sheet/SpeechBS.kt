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
import androidx.compose.ui.unit.dp
import com.count_out.domain.entity.enums.PartName
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
import com.count_out.presentation.models.LauncherBSp
import com.count_out.presentation.models.TypeKeyboard
import com.count_out.presentation.screens.plan.PlanEvent
import com.count_out.presentation.view_element.ButtonConfirm
import com.count_out.presentation.view_element.ModalBottomSheetApp
import com.count_out.presentation.view_element.TextApp
import com.count_out.presentation.view_element.TextFieldApp

@Composable fun SpeechBS(dataState: BottomSheetInterface, elements: List<Domain> = emptyList()){
    dataState.item = elements.firstNotNullOf{it}
    if (dataState.item != null) {
        dataState.nameSection = nameSection1( dataState.item ?: Domain.EMPTY )
        dataState.onDismiss = { dataState.event(PlanEvent.Launcher(LauncherBSp())) }
        dataState.onConfirmation = { speechKit ->
            if (speechKit is SpeechKit) {
                dataState.event(PlanEvent.UpdateSpeech(speechKit.beforeStart))
                dataState.event(PlanEvent.UpdateSpeech(speechKit.afterStart))
                dataState.event(PlanEvent.UpdateSpeech(speechKit.beforeEnd))
                dataState.event(PlanEvent.UpdateSpeech(speechKit.afterEnd))
                dataState.event(PlanEvent.Launcher(LauncherBSp()))
            }
        }
        BottomSheetSpeech1(dataState)
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable fun BottomSheetSpeech1(state: BottomSheetInterface) {
    val stateBS by remember{ mutableStateOf( BottomSheetState().fromPlanState(state)) }
    val sheetState = rememberModalBottomSheetState( skipPartiallyExpanded = true )
    ModalBottomSheetApp(
        onDismissRequest = { stateBS.onDismiss() },
        modifier = Modifier.padding(horizontal = Dimen.bsPaddingHor1),
        shape = shapes.small,
        sheetState = sheetState,
        content = { BottomSheetSpeechContent1(stateBS) }
    )
}

@Composable fun BottomSheetSpeechContent1(stateBS: BottomSheetState) {
    Column( horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(6.dp)) {
//        SelectOtherSpeech(uiState)
        Spacer(Modifier.height(Dimen.bsSpacerHeight))
        FieldTextForSpeech( enterValue = stateBS.enteredBeforeStart,
            nameSection = stringResource(id = R.string.message_before_start) + " " + stateBS.nameSection)
        Spacer(Modifier.height(Dimen.bsSpacerHeight))
        FieldTextForSpeech( enterValue = stateBS.enteredAfterStart,
            nameSection = stringResource(id = R.string.message_after_start) + " " + stateBS.nameSection)
        Spacer(Modifier.height(Dimen.bsSpacerHeight))
        FieldTextForSpeech( enterValue = stateBS.enteredBeforeEnd,
            nameSection = stringResource(id = R.string.message_before_end) + " " + stateBS.nameSection)
        Spacer(Modifier.height(Dimen.bsSpacerHeight))
        FieldTextForSpeech(
            enterValue = stateBS.enteredAfterEnd,
            nameSection = stringResource(id = R.string.message_after_end) + " " + stateBS.nameSection )
        Spacer(Modifier.height(Dimen.bsConfirmationButtonTopHeight))
        ButtonOK(stateBS)
        Spacer(Modifier.height(Dimen.bsSpacerBottomHeight))
    }
}
@Composable fun nameSection1(item: Domain): String{
    return when( item){
        is Plan-> stringResource(id = R.string.training,)
        is Part-> partExactly1(item )
        is Ring-> stringResource(id = R.string.ring,)
        is Exercise-> stringResource(id = R.string.exercise2,)
        is Set -> stringResource(id = R.string.set,)
        else -> ""
    }
}
@Composable fun partExactly1(part: Part): String{
    return when( part.name){
        PartName.WorkUp-> stringResource(id = PartName.WorkUp.idName)
        PartName.WorkOut-> stringResource(id = PartName.WorkOut.idName)
        PartName.WorkDown-> stringResource(id = PartName.WorkDown.idName)
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