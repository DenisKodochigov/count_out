package com.count_out.presentation.view_element.bottom_sheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.count_out.presentation.models.ActivityImpl
import com.count_out.presentation.screens.prime.Action
import com.count_out.presentation.screens.settings.SettingsEvent
import com.count_out.presentation.screens.settings.SettingsState
import com.count_out.presentation.view_element.ButtonConfirm
import com.count_out.presentation.view_element.ModalBottomSheetApp

@OptIn(ExperimentalMaterial3Api::class)
@Composable fun BottomSheetAddActivity(dataState: SettingsState, action: Action) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true, confirmValueChange = { true },)
    ModalBottomSheetApp(
        onDismissRequest = { dataState.onDismissAddActivity(dataState) },
        modifier = Modifier.padding(horizontal = 12.dp),
        shape = MaterialTheme.shapes.small,
        sheetState = sheetState,
        content = { BottomSheetAddActivityContent(dataState, action) }
    )
}
@Composable fun BottomSheetAddActivityContent(uiState: SettingsState, action: Action) {
    Column( horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().padding(12.dp))
    {
        Spacer(Modifier.height(12.dp))
        uiState.activity.value?.let { act->
            ActivityInfoFull(
                activity = mutableStateOf(act as ActivityImpl),
                onChange = { action.ex(SettingsEvent.UpdateActivity(it)) },
            )
        }

        Spacer(Modifier.height(12.dp))
        ButtonConfirm( onConfirm = { uiState.onConfirmAddActivity(uiState) } )
        Spacer(Modifier.height(12.dp))
    }
}