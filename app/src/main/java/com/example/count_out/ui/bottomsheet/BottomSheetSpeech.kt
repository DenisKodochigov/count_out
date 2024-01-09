package com.example.count_out.ui.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.contentColorFor
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
import com.example.count_out.entity.BottomSheetInterface
import com.example.count_out.entity.Exercise
import com.example.count_out.entity.Round
import com.example.count_out.entity.Training
import com.example.count_out.entity.TypeKeyboard
import com.example.count_out.ui.joint.ButtonConfirm
import com.example.count_out.ui.theme.Dimen
import com.example.count_out.ui.theme.interLight12
import com.example.count_out.ui.theme.shapesApp
import com.example.count_out.ui.view_components.TextApp
import com.example.count_out.ui.view_components.TextFieldApp

@OptIn(ExperimentalMaterial3Api::class)
@Composable fun BottomSheetSpeech(itemSpeech: BottomSheetInterface)
{
    val uiState by remember{ mutableStateOf( BottomSheetState()) }
    init(uiState, itemSpeech)

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true, confirmValueChange = { true },)
    ModalBottomSheet(
        onDismissRequest = {uiState.onDismissSpeech.invoke()},
        modifier = Modifier.padding(horizontal = Dimen.bsPaddingHor1),
        shape = shapesApp.small,
        containerColor = BottomSheetDefaults.ContainerColor,
        contentColor = contentColorFor(BottomAppBarDefaults.containerColor),
        tonalElevation = BottomSheetDefaults.Elevation,
        scrimColor = BottomSheetDefaults.ScrimColor,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        windowInsets = BottomSheetDefaults.windowInsets,
        sheetState = sheetState,
        content = { BottomSheetSpeechContent(uiState) })
}

@Composable
fun BottomSheetSpeechContent(uiState: BottomSheetState)
{
    Column( horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimen.bsItemPaddingHor)
    ) {
        SelectOtherSpeech(uiState)
        Spacer(Modifier.height(Dimen.bsSpacerHeight))
        FieldTextForSpeech(uiState.enteredBeforeStart,
            stringResource(id = R.string.message_before_start) + " " + uiState.nameSection)
        Spacer(Modifier.height(Dimen.bsSpacerHeight))
        FieldTextForSpeech(uiState.enteredAfterStart,
            stringResource(id = R.string.message_after_start) + " " + uiState.nameSection)
        Spacer(Modifier.height(Dimen.bsSpacerHeight))
        FieldTextForSpeech(uiState.enteredBeforeEnd,
            stringResource(id = R.string.message_before_end) + " " + uiState.nameSection)
        Spacer(Modifier.height(Dimen.bsSpacerHeight))
        FieldTextForSpeech(uiState.enteredAfterEnd,
            stringResource(id = R.string.message_after_end) + " " + uiState.nameSection)
        ButtonOK(uiState)
        Spacer(Modifier.height(Dimen.bsSpacerBottomHeight))
    }
}

@Composable fun SelectOtherSpeech(uiState: BottomSheetState){

}
@Composable fun FieldTextForSpeech(enterValue: MutableState<String>, nameSection: String){
    Column {
        TextApp(text = nameSection, style = interLight12)
        TextFieldApp(
            textStyle = interLight12,
            modifier = Modifier.fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.primary, shape = shapesApp.extraSmall),
            enterValue = enterValue,
            typeKeyboard = TypeKeyboard.TEXT)
    }
}

fun init(uiState: BottomSheetState, itemSpeech: BottomSheetInterface){

    val speech = when (itemSpeech.item) {
        is Training-> (itemSpeech.item as Training).speech
        is Exercise-> (itemSpeech.item as Exercise).speech
        is Round-> (itemSpeech.item as Round).speech
        else -> SpeechDB()
    }

    uiState.enteredBeforeStart.value = speech.soundBeforeStart
    uiState.enteredBeforeEnd.value = speech.soundBeforeEnd
    uiState.enteredAfterStart.value = speech.soundAfterStart
    uiState.enteredAfterEnd.value = speech.soundAfterEnd

    uiState.listSpeech = itemSpeech.listSpeech
    uiState.nameSection = itemSpeech.nameSection
    uiState.item = itemSpeech.item
    uiState.onConfirmationSpeech = itemSpeech.onConfirmationSpeech
    uiState.onDismissSpeech = itemSpeech.onDismissSpeech
}
@Composable
fun ButtonOK(uiState: BottomSheetState){
    ButtonConfirm(onConfirm = {
        uiState.onConfirmationSpeech(
            SpeechDB(
                soundBeforeStart = uiState.enteredBeforeStart.value,
                soundAfterStart = uiState.enteredAfterStart.value,
                soundBeforeEnd = uiState.enteredBeforeEnd.value,
                soundAfterEnd = uiState.enteredAfterEnd.value
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