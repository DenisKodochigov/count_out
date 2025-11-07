package com.count_out.presentation.screens.plan

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.Exercise
import com.count_out.presentation.view_element.TextApp
import com.count_out.presentation.view_element.borderMy
import com.count_out.presentation.view_element.bottom_sheet.ShowBottomSheetSelectActivity
import com.count_out.presentation.view_element.bottom_sheet.ShowBottomSheetSpeech

@Composable fun CarcassTuningSet(
    columnRight: @Composable () -> Unit,
    columnLeft: @Composable () -> Unit,
    button: @Composable () -> Unit
){
    var rightHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    Row(modifier = Modifier.fillMaxWidth().padding(2.dp)) {
        Column( modifier = Modifier.height(rightHeight).width(IntrinsicSize.Min).padding(end = 8.dp)){
            Column(
                content = { columnLeft()},
                modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()))
            Column( ) { button() }
        }
        Column( content = { columnRight() },
            modifier = Modifier.weight(1f).padding(horizontal = 1.dp)
                .onGloballyPositioned { rightHeight = with(density) { it.size.height.toDp() } }
        )
    }
}
@Composable fun CarcassTitle(
    onCollapsing: @Composable () ->Unit,
    nameItem: @Composable () ->Unit,
    infoItem: @Composable () ->Unit,
    actionItem: @Composable () ->Unit,
){
    Row( verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(end = 6.dp)){
        onCollapsing()
        Spacer(modifier = Modifier.width(2.dp))
        Column(modifier = Modifier.weight(1f)) {
            Row(
//                modifier = Modifier.fillMaxWidth(),
                content = {nameItem()})
            infoItem()
        }
//        Spacer(modifier = Modifier.weight(1f))
        actionItem()
        Spacer(modifier = Modifier.width(6.dp))
    }
}

@Composable fun CarcassLeftList(
    dataState: PlanState,
    list: List<Domain>,
    textItem: @Composable (Int)-> String,
){
    Column {
        list.forEachIndexed { ind, item ->
            CarcassLeftListItems(dataState = dataState,
                item = item,
                text = textItem(ind),
                onLongClick = { println("Долгое нажатие")},
                onClick = { setSelecting( dataState,item,list) })
        }
    }
}

@Composable fun CarcassLeftListItems(
    dataState: PlanState,
    item: Domain,
    text: String,
    onLongClick: () ->Unit,
    onClick: () ->Unit,
){
    ShowBottomSheetSpeech(dataState,dataState.showBS.exercise, item)
    if (item is Exercise) ShowBottomSheetSelectActivity(dataState, item)
    val selected = getSelecting(dataState, item)
    Row( horizontalArrangement = Arrangement.Start,
        modifier= Modifier
            .borderMy( selected, color = colorScheme.surfaceContainerLow)
//            .padding(horizontal = 4.dp)
            .combinedClickable(onLongClick = onLongClick, onClick = onClick ,)
    ) {
        TextApp( modifier = Modifier.width(60.dp).padding(horizontal = 2.dp),
            textAlign = TextAlign.Center,
            text = text,
            style = typography.titleMedium)
    }
}
//Title(
//    onCollapsing = {},
//    nameItem = {},
//    infoItem = {},
//    actionItem = {}
//)
//
////    ColumnDragDrop(
////        items = ring.exercises,
////        modifier = Modifier.padding(end = 4.dp),
////        content = { item -> RingCardBodyExerciseListItem(item, dataState, ring) },
////        onMoveItem = { from, to ->
////            Log.d("KDS", " from=$from   to=$to")
////            dataState.event(
////                PlanEvent.ChangeSequenceExercise(SetViewId(ring.idRing,from,to)))
////        }
////    )