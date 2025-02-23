package com.count_out.presentation.bottom_sheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.count_out.app.R
import com.count_out.app.presentation.screens.executor.ExecuteWorkoutScreenState
import com.count_out.app.ui.theme.Dimen
import com.count_out.app.ui.theme.mTypography
import com.count_out.app.ui.theme.shapes
import com.count_out.app.ui.view_components.ButtonsOkCancel
import com.count_out.app.ui.view_components.ModalBottomSheetApp
import com.count_out.app.ui.view_components.TextApp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetSaveTraining(uiState: ExecuteWorkoutScreenState)
{
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true, confirmValueChange = { true },)
    ModalBottomSheetApp(
        onDismissRequest = { uiState.onDismissSaveTraining.invoke(uiState) },
        modifier = Modifier.padding(horizontal = Dimen.bsPaddingHor1),
        shape = shapes.small,
        sheetState = sheetState,
        content = { BottomSheetSaveTrainingContent(uiState) }
    )
}
@Composable
fun BottomSheetSaveTrainingContent(uiState: ExecuteWorkoutScreenState)
{
    Column( horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimen.bsItemPaddingHor)
    ) {
        Spacer(Modifier.height(Dimen.bsSpacerHeight))
        TextApp(text = stringResource(R.string.save_traning), style = mTypography.headlineSmall)
        Spacer(Modifier.height(Dimen.bsSpacerHeight))
        ButtonsOkCancel( onConfirm = { uiState.onConfirmASaveTraining(uiState) },
            onDismiss = { uiState.onDismissSaveTraining(uiState)})
        Spacer(Modifier.height(Dimen.bsSpacerBottomHeight))
    }
}