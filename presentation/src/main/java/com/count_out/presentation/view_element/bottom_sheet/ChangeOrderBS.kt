package com.count_out.presentation.view_element.bottom_sheet

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.count_out.domain.entity.SetViewId
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Plan
import com.count_out.domain.entity.workout.Ring
import com.count_out.presentation.R
import com.count_out.presentation.models.BottomSheetInterface
import com.count_out.presentation.models.Dimen
import com.count_out.presentation.models.Dimen.contourAll2
import com.count_out.presentation.models.LauncherBSp
import com.count_out.presentation.screens.plans.model.PlansEvent
import com.count_out.presentation.view_element.ModalBottomSheetApp
import com.count_out.presentation.view_element.TextApp
import com.count_out.presentation.view_element.custom_view.Frame
import com.count_out.presentation.view_element.drag_drop_column.column.ColumnDragDrop

@OptIn(ExperimentalMaterial3Api::class)
@Composable fun ChangeOrderBS(dataState: BottomSheetInterface, idOwner: Long, owner: Domain, list: List<Domain>){
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
        ){
            CarcassBS(
                list = { list },
                viewElementList = {item -> ViewElementList(item)},
                actionOnMove = { from, to -> dataState.onConfirmation(SetViewId( idOwner, owner, from, to)) }
            )
        }
    }
}

@Composable fun CarcassBS(
    list: @Composable ()-> List<Domain>,
    viewElementList: @Composable (Domain)-> Unit,
    actionOnMove: (Int, Int)-> Unit,
){
    ColumnDragDrop(
        items = list(),
        modifier = Modifier.padding(horizontal = 6.dp),
        content = { item -> viewElementList(item) },
        onMoveItem = { from, to ->
            Log.d("KDS", " from=$from   to=$to")
            actionOnMove(from, to)
        }
    )
}
@Composable fun ViewElementList(item: Domain){
    val name = when(item){
        is Plan ->{ "${item.idView}:${item.name}" }
        is Ring ->{ "${stringResource(R.string.ring)}:${item.idView}" }
        is Exercise ->{ "${item.idView}:${item.activity.name}" }
        else -> "No domain"
    }
    Frame(contour = contourAll2, modifier = Modifier.padding(bottom = 12.dp).fillMaxWidth(),
        background = Color.Transparent){
        Row(horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth().padding(start = 12.dp, top = 4.dp, bottom = 4.dp)){
            TextApp(text = name, style = MaterialTheme.typography.headlineMedium)
        }
    }
}