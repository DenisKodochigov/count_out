package com.example.count_out.ui.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.count_out.entity.Set
import com.example.count_out.entity.Training
import com.example.count_out.entity.TypeKeyboard
import com.example.count_out.ui.joint.ButtonConfirm
import com.example.count_out.ui.theme.Dimen
import com.example.count_out.ui.theme.interLight12Start
import com.example.count_out.ui.theme.shapesApp
import com.example.count_out.ui.view_components.TextApp
import com.example.count_out.ui.view_components.TextFieldApp

@OptIn(ExperimentalMaterial3Api::class)
@Composable fun BottomSheetSpeech(itemSpeech: BottomSheetInterface)
{
    val uiState by remember{ mutableStateOf( bottomSheetStateNew(itemSpeech)) }

    val sheetState = rememberModalBottomSheetState( skipPartiallyExpanded = true, )
//        confirmValueChange = { true },)
    
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

fun bottomSheetStateNew(itemSpeech: BottomSheetInterface): BottomSheetState{
    val speech = when (itemSpeech.item) {
        is Training-> (itemSpeech.item as Training).speech
        is Exercise-> (itemSpeech.item as Exercise).speech
        is Round-> (itemSpeech.item as Round).speech
        is Set-> (itemSpeech.item as Set).speech
        else -> SpeechDB()
    }
    return BottomSheetState(
        enteredBeforeStart = mutableStateOf( speech.soundBeforeStart ),
        enteredBeforeEnd = mutableStateOf( speech.soundBeforeEnd ),
        enteredAfterStart = mutableStateOf( speech.soundAfterStart ),
        enteredAfterEnd = mutableStateOf( speech.soundAfterEnd ),
        speechId = speech.idSpeech ,
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
        SelectOtherSpeech(uiState)
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
        TextApp(text = nameSection, style = interLight12Start)
        TextFieldApp(
            textStyle = interLight12Start,
            showLine = true,
            onLossFocus = false,
            contentAlignment = Alignment.BottomStart,
            modifier = Modifier.fillMaxWidth(),
//                .background(color = MaterialTheme.colorScheme.primary, shape = shapesApp.extraSmall),
            placeholder = enterValue.value,
            onChangeValue = { enterValue.value = it },
            typeKeyboard = TypeKeyboard.TEXT )
    }
}
@Composable fun SelectOtherSpeech(uiState: BottomSheetState){

}
@Composable
fun ButtonOK(uiState: BottomSheetState)
{
    ButtonConfirm(onConfirm = {
        uiState.onConfirmationSpeech(
            SpeechDB(
                idSpeech = uiState.speechId,
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