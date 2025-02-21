package com.count_out.app.ui.bottom_sheet

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
import com.count_out.app.presentation.models.ActivityImpl
import com.count_out.app.presentation.screens.settings.SettingScreenState
import com.count_out.app.ui.theme.Dimen
import com.count_out.app.ui.theme.shapes
import com.count_out.app.ui.view_components.ButtonConfirm
import com.count_out.app.ui.view_components.ModalBottomSheetApp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetAddActivity(uiState: SettingScreenState)
{
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true, confirmValueChange = { true },)
    ModalBottomSheetApp(
        onDismissRequest = { uiState.onDismissAddActivity(uiState) },
        modifier = Modifier.padding(horizontal = Dimen.bsPaddingHor1),
        shape = shapes.small,
        sheetState = sheetState,
        content = { BottomSheetAddActivityContent(uiState) }
    )
}
@Composable fun BottomSheetAddActivityContent(uiState: SettingScreenState) {
    Column( horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().padding(Dimen.bsItemPaddingHor))
    {
        Spacer(Modifier.height(Dimen.bsSpacerHeight))
        uiState.activity.value?.let { act->
            ActivityInfoFull(
                activity = mutableStateOf(act as ActivityImpl),
                onChange = { uiState.onUpdateActivity(it) },
                onChangeColor = { uiState.onSetColorActivity(act.idActivity, it ) }
            )
        }

        Spacer(Modifier.height(Dimen.bsSpacerHeight))
        ButtonConfirm( onConfirm = { uiState.onConfirmAddActivity(uiState) } )
        Spacer(Modifier.height(Dimen.bsSpacerBottomHeight))
    }
}