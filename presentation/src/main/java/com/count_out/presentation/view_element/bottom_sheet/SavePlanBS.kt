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
import com.count_out.domain.entity.workout.Domain
import com.count_out.presentation.R
import com.count_out.presentation.models.BottomSheetInterface
import com.count_out.presentation.models.Dimen
import com.count_out.presentation.models.LauncherBSp
import com.count_out.presentation.screens.execute.ExecuteEvent
import com.count_out.presentation.view_element.ButtonsOkCancel
import com.count_out.presentation.view_element.ModalBottomSheetApp
import com.count_out.presentation.view_element.TextApp

@OptIn(ExperimentalMaterial3Api::class)
@Composable fun SavePlanBS(dataState: BottomSheetInterface, owner: Domain) {
    dataState.item = owner
    if (dataState.item != null) {
        ModalBottomSheetApp(
            onDismissRequest = { dataState.event(ExecuteEvent.Launcher(LauncherBSp())) },
            modifier = Modifier.padding(horizontal = Dimen.bsPaddingHor1),
            shape = shapes.small,
            sheetState = rememberModalBottomSheetState(
                skipPartiallyExpanded = true,
                confirmValueChange = { true }),
            content = { BottomSheetSaveTrainingContent(dataState) }
        )
    }
}
@Composable
fun BottomSheetSaveTrainingContent(dataState: BottomSheetInterface)
{
    Column( horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().padding(Dimen.bsItemPaddingHor)
    ) {
        Spacer(Modifier.height(Dimen.bsSpacerHeight))
        TextApp(text = stringResource(R.string.save_traning), style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(Dimen.bsSpacerHeight))
        ButtonsOkCancel( onConfirm = { dataState.event(ExecuteEvent.Save) },
            onDismiss = { dataState.event(ExecuteEvent.Launcher(LauncherBSp())) })
        Spacer(Modifier.height(Dimen.bsSpacerBottomHeight))
    }
}