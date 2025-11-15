package com.count_out.presentation.view_element.bottom_sheet

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.count_out.domain.entity.SetViewId
import com.count_out.domain.entity.workout.Domain
import com.count_out.presentation.models.BottomSheetInterface
import com.count_out.presentation.models.Dimen
import com.count_out.presentation.models.LauncherBSp
import com.count_out.presentation.screens.plans.model.PlansEvent
import com.count_out.presentation.view_element.ModalBottomSheetApp
import com.count_out.presentation.view_element.drag_drop_column.column.ColumnDragDrop

@Composable fun CarcassBS(
    list: @Composable ()-> List<Domain>,
    viewElementList: @Composable (Domain)-> Unit,
    actionOnMove: (Int, Int)-> Unit,
){
    ColumnDragDrop(
        items = list(),
        modifier = Modifier.padding(end = 4.dp),
        content = { item -> viewElementList(item) },
        onMoveItem = { from, to ->
            Log.d("KDS", " from=$from   to=$to")
            actionOnMove(from, to)
        }
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable fun ChangeOrderShowBS(dataState: BottomSheetInterface, owner: Domain, list: List<Domain>){
    if (list.isNotEmpty()){
        dataState.onDismiss = { dataState.event(PlansEvent.Launcher(LauncherBSp())) }
        dataState.onConfirmation = { setViewId ->
            dataState.event( PlansEvent.ChangeSequenceExercise(setViewId as SetViewId))
            dataState.event(PlansEvent.Launcher(LauncherBSp()))
        }
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded=true, confirmValueChange={true})

        ModalBottomSheetApp(
            onDismissRequest = { dataState.onDismiss()},
            modifier = Modifier.padding(horizontal = Dimen.bsPaddingHor1),
            shape = MaterialTheme.shapes.small,
            sheetState = sheetState,
            content = {
                CarcassBS(
                    list = { list },
                    viewElementList = {},
                    actionOnMove = { from, to ->
                        dataState.onConfirmation(SetViewId(1L, null, from, to))
                    },
                )
            }
        )
    }
}