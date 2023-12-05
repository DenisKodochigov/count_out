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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.example.count_out.R
import com.example.count_out.entity.TagsTesting
import com.example.count_out.entity.TypeKeyboard
import com.example.count_out.entity.TypeText
import com.example.count_out.ui.joint.ButtonConfirm
import com.example.count_out.ui.screens.workouts.WorkoutsScreenState
import com.example.count_out.ui.theme.Dimen
import com.example.count_out.ui.theme.shapesApp
import com.example.count_out.ui.theme.styleApp
import com.example.count_out.ui.view_components.HeaderScreen
import com.example.count_out.ui.view_components.MyOutlinedTextFieldWithoutIcon
import com.example.count_out.ui.view_components.TextApp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetWorkoutAdd(uiState: WorkoutsScreenState)
{
    uiState.enteredName.value =""
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true, confirmValueChange = { true },)
    ModalBottomSheet(
        onDismissRequest = uiState.onDismiss,
        modifier = Modifier.padding(horizontal = Dimen.bsPaddingHor1),
        shape = shapesApp.small,
        containerColor = BottomSheetDefaults.ContainerColor,
        contentColor = contentColorFor(BottomAppBarDefaults.containerColor),
        tonalElevation = BottomSheetDefaults.Elevation,
        scrimColor = BottomSheetDefaults.ScrimColor,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        windowInsets = BottomSheetDefaults.windowInsets,
        sheetState = sheetState,
        content = { BottomSheetBasketAddContent(uiState) })
}

@Composable
fun BottomSheetBasketAddContent(uiState: WorkoutsScreenState)
{
    Column( horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().padding(Dimen.bsItemPaddingHor)
    ) {
        HeaderScreen(text = stringResource(R.string.new_workout))
        Spacer(Modifier.height(Dimen.bsSpacerHeight))
        FieldNameBasket(uiState)
        Spacer(modifier = Modifier.height(Dimen.bsConfirmationButtonTopHeight))
        ButtonOK(uiState)
        Spacer(Modifier.height(Dimen.bsSpacerBottomHeight))
    }
}

@Composable
fun FieldNameBasket(uiState: WorkoutsScreenState)
{
    MyOutlinedTextFieldWithoutIcon(
        modifier = Modifier.fillMaxWidth().testTag(TagsTesting.BASKET_BS_INPUT_NAME),
        enterValue = uiState.enteredName,
        typeKeyboard = TypeKeyboard.TEXT,
        label = {
            TextApp(text = stringResource(R.string.new_workout),
                style = styleApp( nameStyle = TypeText.NAME_SECTION)
            )},
        keyboardActionsOnDone = {
            uiState.onAddClick(uiState.enteredName.value)
            uiState.enteredName.value = ""
            uiState.onDismiss()
        }
    )
}
@Composable
fun ButtonOK(uiState: WorkoutsScreenState){
    ButtonConfirm(onConfirm = {
        uiState.onAddClick(uiState.enteredName.value)
        uiState.onDismiss() })
}