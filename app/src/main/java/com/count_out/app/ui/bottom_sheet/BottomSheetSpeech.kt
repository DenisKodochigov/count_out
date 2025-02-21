package com.count_out.app.ui.bottom_sheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.count_out.app.R
import com.count_out.app.entity.TypeKeyboard
import com.count_out.app.presentation.models.SpeechImpl
import com.count_out.app.presentation.models.SpeechKitImpl
import com.count_out.app.ui.modules.BottomSheetInterface
import com.count_out.app.ui.theme.Dimen
import com.count_out.app.ui.theme.mTypography
import com.count_out.app.ui.theme.shapes
import com.count_out.app.ui.view_components.ButtonConfirm
import com.count_out.app.ui.view_components.ModalBottomSheetApp
import com.count_out.app.ui.view_components.TextApp
import com.count_out.app.ui.view_components.TextFieldApp
import com.count_out.domain.entity.Exercise
import com.count_out.domain.entity.Round
import com.count_out.domain.entity.Set
import com.count_out.domain.entity.Training

@OptIn(ExperimentalMaterial3Api::class)
@Composable fun BottomSheetSpeech(itemSpeech: BottomSheetInterface)
{
    val uiState by remember{ mutableStateOf( bottomSheetStateNew(itemSpeech)) }
    val sheetState = rememberModalBottomSheetState( skipPartiallyExpanded = true )
    ModalBottomSheetApp(
        onDismissRequest = {uiState.onDismissSpeech.invoke()},
        modifier = Modifier.padding(horizontal = Dimen.bsPaddingHor1),
        shape = shapes.small,
        sheetState = sheetState,
        content = { BottomSheetSpeechContent(uiState) }
    )
}

fun bottomSheetStateNew(itemSpeech: BottomSheetInterface): BottomSheetState{
    val speech = when (itemSpeech.item) {
        is Training -> (itemSpeech.item as Training).speech
        is Exercise -> (itemSpeech.item as Exercise).speech
        is Round -> (itemSpeech.item as Round).speech
        is Set -> (itemSpeech.item as Set).speech
        else -> null
    }
    return BottomSheetState(
        enteredBeforeStart = mutableStateOf( speech?.beforeStart?.message ?: ""),
        enteredBeforeEnd = mutableStateOf( speech?.beforeEnd?.message ?: "" ),
        enteredAfterStart = mutableStateOf( speech?.afterStart?.message ?: "" ),
        enteredAfterEnd = mutableStateOf( speech?.afterEnd?.message ?: "" ),
        speechKit= speech ,
        listSpeech = itemSpeech.listSpeech,
        nameSection = itemSpeech.nameSection,
        item = itemSpeech.item,
        onConfirmationSpeech = itemSpeech.onConfirmationSpeech,
        onDismissSpeech = itemSpeech.onDismissSpeech,
    )
}
@Composable fun BottomSheetSpeechContent(uiState: BottomSheetState) {
    Column( horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(Dimen.bsItemPaddingHor)
    ) {
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
            textStyle = mTypography.bodyLarge,
            showLine = true,
            onLossFocus = false,
            maxLines = 3,
            edit = true,
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier.fillMaxWidth(),
            placeholder = enterValue.value,
            onChangeValue = { enterValue.value = it },
            typeKeyboard = TypeKeyboard.TEXT )
        TextApp(text = nameSection, style = mTypography.bodySmall.copy(fontWeight = FontWeight.ExtraLight),
            modifier = Modifier.padding(start = Dimen.bsItemPaddingHor))
    }
}
@Composable fun ButtonOK(uiState: BottomSheetState) {
    ButtonConfirm(onConfirm = {
        uiState.onConfirmationSpeech(
            SpeechKitImpl(
                idSpeechKit = uiState.speechKit?.idSpeechKit ?: 0,
                idBeforeStart = uiState.speechKit?.idBeforeStart ?: 0,
                idAfterStart = uiState.speechKit?.idAfterStart ?: 0,
                idBeforeEnd = uiState.speechKit?.idBeforeEnd ?: 0,
                idAfterEnd = uiState.speechKit?.idAfterEnd ?: 0,
                beforeStart = (uiState.speechKit?.beforeStart as SpeechImpl).copy(message = uiState.enteredBeforeStart.value),
                afterStart = (uiState.speechKit?.afterStart as SpeechImpl).copy(message = uiState.enteredAfterStart.value),
                beforeEnd = (uiState.speechKit?.beforeEnd as SpeechImpl).copy(message = uiState.enteredBeforeEnd.value),
                afterEnd = (uiState.speechKit?.afterEnd as SpeechImpl).copy(message = uiState.enteredAfterEnd.value)
            ),
            uiState.item
        )
        uiState.onDismissSpeech.invoke()
    })
}

@Preview(showBackground = true)
@Composable fun BottomSheetSpeechContentPreview() {
    BottomSheetSpeechContent(BottomSheetState())
}