package com.example.count_out.ui.bottomsheet

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
import androidx.compose.ui.tooling.preview.Preview
import com.example.count_out.R
import com.example.count_out.data.room.tables.SpeechDB
import com.example.count_out.data.room.tables.SpeechKitDB
import com.example.count_out.entity.BottomSheetInterface
import com.example.count_out.entity.Exercise
import com.example.count_out.entity.Round
import com.example.count_out.entity.Set
import com.example.count_out.entity.Training
import com.example.count_out.entity.TypeKeyboard
import com.example.count_out.ui.theme.Dimen
import com.example.count_out.ui.theme.interLight14Start
import com.example.count_out.ui.theme.interThin12Start
import com.example.count_out.ui.theme.shapes
import com.example.count_out.ui.view_components.ButtonConfirm
import com.example.count_out.ui.view_components.ModalBottomSheetApp
import com.example.count_out.ui.view_components.TextApp
import com.example.count_out.ui.view_components.TextFieldApp

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
        is Training-> (itemSpeech.item as Training).speech
        is Exercise-> (itemSpeech.item as Exercise).speech
        is Round-> (itemSpeech.item as Round).speech
        is Set-> (itemSpeech.item as Set).speech
        else -> SpeechKitDB()
    }
    return BottomSheetState(
        enteredBeforeStart = mutableStateOf( speech.beforeStart.message ),
        enteredBeforeEnd = mutableStateOf( speech.beforeEnd.message ),
        enteredAfterStart = mutableStateOf( speech.afterStart.message ),
        enteredAfterEnd = mutableStateOf( speech.afterEnd.message ),
        speechKit= speech ,
        listSpeech = itemSpeech.listSpeech,
        nameSection = itemSpeech.nameSection,
        item = itemSpeech.item,
        onConfirmationSpeech = itemSpeech.onConfirmationSpeech,
        onDismissSpeech = itemSpeech.onDismissSpeech,
    )
}
@Composable
fun BottomSheetSpeechContent(uiState: BottomSheetState)
{
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
    Column {
        TextFieldApp(
            textStyle = interLight14Start,
            showLine = true,
            onLossFocus = false,
            maxLines = 3,
            edit = true,
            contentAlignment = Alignment.BottomStart,
            modifier = Modifier.fillMaxWidth(),
            placeholder = enterValue.value,
            onChangeValue = { enterValue.value = it },
            typeKeyboard = TypeKeyboard.TEXT )
        TextApp(text = nameSection, style = interThin12Start,
            modifier = Modifier.padding(start = Dimen.bsItemPaddingHor))
    }
}
@Composable
fun ButtonOK(uiState: BottomSheetState)
{
    ButtonConfirm(onConfirm = {
        uiState.onConfirmationSpeech(
            SpeechKitDB(
                idSpeechKit = uiState.speechKit.idSpeechKit,
                idBeforeStart = uiState.speechKit.idBeforeStart,
                idAfterStart = uiState.speechKit.idAfterStart,
                idBeforeEnd = uiState.speechKit.idBeforeEnd,
                idAfterEnd = uiState.speechKit.idAfterEnd,
                beforeStart = (uiState.speechKit.beforeStart as SpeechDB).copy(message = uiState.enteredBeforeStart.value),
                afterStart = (uiState.speechKit.afterStart as SpeechDB).copy(message = uiState.enteredAfterStart.value),
                beforeEnd = (uiState.speechKit.beforeEnd as SpeechDB).copy(message = uiState.enteredBeforeEnd.value),
                afterEnd = (uiState.speechKit.afterEnd as SpeechDB).copy(message = uiState.enteredAfterEnd.value)
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