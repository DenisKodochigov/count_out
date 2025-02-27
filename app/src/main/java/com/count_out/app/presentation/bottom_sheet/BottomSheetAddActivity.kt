package com.count_out.app.presentation.bottom_sheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.count_out.app.presentation.theme.Dimen
import com.count_out.app.presentation.theme.shapes
import com.count_out.app.presentation.models.ActivityImpl
import com.count_out.app.presentation.prime.Action
import com.count_out.app.presentation.screens.settings.SettingsEvent
import com.count_out.app.presentation.screens.settings.SettingsState
import com.count_out.app.presentation.view_components.ButtonConfirm
import com.count_out.app.presentation.view_components.ModalBottomSheetApp

@OptIn(ExperimentalMaterial3Api::class)
@Composable fun BottomSheetAddActivity(dataState: SettingsState, action: Action) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true, confirmValueChange = { true },)
    ModalBottomSheetApp(
        onDismissRequest = { dataState.onDismissAddActivity(dataState) },
        modifier = Modifier.padding(horizontal = Dimen.bsPaddingHor1),
        shape = shapes.small,
        sheetState = sheetState,
        content = { BottomSheetAddActivityContent(dataState, action) }
    )
}
@Composable fun BottomSheetAddActivityContent(uiState: SettingsState, action: Action) {
    Column( horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().padding(Dimen.bsItemPaddingHor))
    {
        Spacer(Modifier.height(Dimen.bsSpacerHeight))
        uiState.activity.value?.let { act->
            ActivityInfoFull(
                activity = mutableStateOf(act as ActivityImpl),
                onChange = { action.ex(SettingsEvent.UpdateActivity(it)) },
            )
        }

        Spacer(Modifier.height(Dimen.bsSpacerHeight))
        ButtonConfirm( onConfirm = { uiState.onConfirmAddActivity(uiState) } )
        Spacer(Modifier.height(Dimen.bsSpacerBottomHeight))
    }
}