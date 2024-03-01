package com.example.count_out.ui.view_components.drag_drop_column

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun <T>ColumnDragDrop(
    items: List<T>,
    modifier: Modifier = Modifier,
    viewItem:@Composable (T) -> Unit,
    showList: Boolean
){
    val localDensity = LocalDensity.current
    var heightIs by remember { mutableStateOf(0.dp) }
    Column(
        modifier = Modifier.onGloballyPositioned { coordinates ->
            heightIs = with(localDensity) { coordinates.size.height.toDp() }
        }
    ){
        items.forEachIndexed { index,  item ->
            AnimatedVisibility(
                modifier = modifier.padding(top = 8.dp),
                visible = showList,
                content = {
                    ItemDrop(
                        frontFon = { viewItem( item ) },
                        indexItem = index,
                        onMoveItem = {},
                        onClickItem = {},
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable fun ItemDrop(
    indexItem:Int,
    frontFon:@Composable () -> Unit,
    onMoveItem: (Int) -> Unit,
    onClickItem: (Int) -> Unit,
){
    val coroutineScope = rememberCoroutineScope()
    val stateDrag by remember{ mutableStateOf(StateDragColumn(indexItem = indexItem)) }

    Box(modifier = Modifier.fillMaxWidth()
    ){
        BackFon( modifier = Modifier.align(alignment = Alignment.Center))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .offset { IntOffset(0, stateDrag.shift.value.roundToInt()) }
                .combinedClickable(
                    onClick = { onClickItem(indexItem) },
                    onLongClick = { stateDrag.enableDrag( true) },
                )
                .draggable(
                    enabled = stateDrag.enableDrag.value,
                    state = rememberDraggableState { delta ->
                        coroutineScope.launch { stateDrag.shiftItem(delta) } },
                    orientation = Orientation.Vertical,
                    onDragStarted = { stateDrag.onStartDrag() },
                    onDragStopped = {
                        stateDrag.enableDrag( false )
                        coroutineScope.launch { stateDrag.onStopDrag(onMoveItem) }
                    }
                )
        ){
            frontFon()
        }
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