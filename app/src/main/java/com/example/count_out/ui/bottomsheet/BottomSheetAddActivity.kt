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
import com.example.count_out.ui.screens.settings.SettingScreenState
import com.example.count_out.ui.theme.Dimen
import com.example.count_out.ui.theme.shapesApp
import com.example.count_out.ui.view_components.ButtonConfirm
import com.example.count_out.ui.view_components.lg

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetAddActivity(uiState: SettingScreenState)
{
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true, confirmValueChange = { true },)
    ModalBottomSheet(
        onDismissRequest = { uiState.onDismissAddActivity.invoke() },
        modifier = Modifier.padding(horizontal = Dimen.bsPaddingHor1),
        shape = shapesApp.small,
        containerColor = BottomSheetDefaults.ContainerColor,
        contentColor = contentColorFor(BottomAppBarDefaults.containerColor),
        tonalElevation = BottomSheetDefaults.Elevation,
        scrimColor = BottomSheetDefaults.ScrimColor,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        windowInsets = BottomSheetDefaults.windowInsets,
        sheetState = sheetState,
        content = { BottomSheetAddActivityContent(uiState) })
}
@Composable
fun BottomSheetAddActivityContent(uiState: SettingScreenState)
{
    lg("BottomSheetAddActivityContent")
    Column( horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimen.bsItemPaddingHor)
    ) {
        Spacer(Modifier.height(Dimen.bsSpacerHeight))
        ActivityValueFull(
            activity = uiState.activity,
            onChange = { uiState.onUpdateActivity(it) },
            onChangeColor = { uiState.onSetColorActivity(uiState.activity.value.idActivity, it ) }
        )
        Spacer(Modifier.height(Dimen.bsSpacerHeight))
        ButtonConfirm( onConfirm = {
            uiState.onConfirmAddActivity(uiState.activity.value)
        } )
        Spacer(Modifier.height(Dimen.bsSpacerBottomHeight))
    }
}