package com.example.count_out.ui.view_components.drag_drop

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.count_out.ui.view_components.log
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemDrop(
    lazyState: LazyListState,
    indexItem:Int,
    frontFon:@Composable () -> Unit,
    onMoveItem: (Int) -> Unit,
){

    val enableDrag = remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()
    val stateDrag by remember{ mutableStateOf(StateDrag(indexItem = indexItem))}

    Box(modifier = Modifier.fillMaxWidth()
    ){
        BackFon( modifier = Modifier.align(alignment = Alignment.Center))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .offset { IntOffset(0, stateDrag.offsetA.value.roundToInt()) }
                .combinedClickable(
                    onClick = { },
                    onLongClick = { enableDrag.value = true },
                )
                .draggable(
                    enabled = enableDrag.value,
                    state = rememberDraggableState { delta ->
                        coroutineScope.launch { offsetControl(delta, stateDrag) }
                    },
                    orientation = Orientation.Vertical,
                    onDragStarted = { onDragStarted(lazyState, stateDrag) },
                    onDragStopped = {
//                        enableDrag.value = false
                        coroutineScope.launch {
                            onDragStopped(lazyState, stateDrag, onMoveItem)
                        }
                    }
                )
        ){
            frontFon()
        }
    }
}
suspend fun offsetControl( delta: Float, stateDrag: StateDrag)
{
    log(false, "offsetLog  offset: ${stateDrag.offset}; delta: $delta; maxOffsetUp to maxOffsetDown ${stateDrag.maxOffsetUp}:${stateDrag.maxOffsetDown}")
    if ((stateDrag.offset + delta) in stateDrag.maxOffsetUp..stateDrag.maxOffsetDown) {
        stateDrag.offsetA.snapTo(stateDrag.offsetA.value + delta)
        stateDrag.offsetA.value.plus(delta)
        stateDrag.offset += delta
    }
}

fun onDragStarted(lazyState: LazyListState, stateDrag: StateDrag)
{
    stateDrag.offset = lazyState.layoutInfo.visibleItemsInfo[stateDrag.indexItem].offset.toFloat()
    stateDrag.maxOffsetDown = lazyState.layoutInfo.visibleItemsInfo.last().offset.toFloat()
}

fun onDragStopped(lazyState: LazyListState, stateDrag: StateDrag, onMoveItem: (Int) -> Unit,)
{
    lazyState.layoutInfo.visibleItemsInfo.firstOrNull { item ->
        stateDrag.offset.toInt() in item.offset .. (item.offset + item.size)
    }?.also { item ->
        if (stateDrag.indexItem != item.index) onMoveItem(item.index)
    }
}
@Composable
fun BackFon(modifier: Modifier = Modifier){
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 12.dp)) {
        Spacer(modifier = Modifier.weight(1f))
    }
}
