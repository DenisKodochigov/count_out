package com.count_out.presentation.view_element.bottom_sheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.count_out.domain.entity.workout.ShowBottomSheet
import com.count_out.domain.entity.workout.Training
import com.count_out.presentation.screens.start_screen.ExecuteState
import com.count_out.presentation.R
import com.count_out.presentation.models.Dimen
import com.count_out.presentation.models.TrainingImplP
import com.count_out.presentation.screens.prime.Action
import com.count_out.presentation.screens.start_screen.ExecuteEvent
import com.count_out.presentation.view_element.ButtonsOkCancel
import com.count_out.presentation.view_element.ModalBottomSheetApp
import com.count_out.presentation.view_element.TextApp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetSaveTraining(dataState: ExecuteState, action: Action)
{
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true, confirmValueChange = { true },)
    ModalBottomSheetApp(
        onDismissRequest = {action.ex(ExecuteEvent.ShowBS(dataState.showBS))},
        modifier = Modifier.padding(horizontal = Dimen.bsPaddingHor1),
        shape = shapes.small,
        sheetState = sheetState,
        content = { BottomSheetSaveTrainingContent(dataState, action) }
    )
}
@Composable
fun BottomSheetSaveTrainingContent(dataState: ExecuteState, action: Action)
{
    Column( horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().padding(Dimen.bsItemPaddingHor)
    ) {
        Spacer(Modifier.height(Dimen.bsSpacerHeight))
        TextApp(text = stringResource(R.string.save_traning), style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(Dimen.bsSpacerHeight))
        ButtonsOkCancel( onConfirm = { action.ex(ExecuteEvent.Save) },
            onDismiss = { action.ex(ExecuteEvent.ShowBS(dataState.showBS))})
        Spacer(Modifier.height(Dimen.bsSpacerBottomHeight))
    }
}