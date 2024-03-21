package com.example.count_out.ui.view_components.drag_drop_column

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun <T>ColumnDragDrop(
    items: List<T>,
    modifier: Modifier = Modifier,
    viewItem:@Composable (T) -> Unit,
    showList: Boolean,
    onMoveItem: (Int, Int) -> Unit = {_,_->},
    onClickItem: (Int) -> Unit = {},
){
    var heightList by remember { mutableIntStateOf(0) }
    Column( modifier = Modifier.onGloballyPositioned { heightList = it.size.height }
    ) {
        items.forEachIndexed { index, item ->
            AnimatedVisibility(
                modifier = modifier.padding(top = 8.dp),
                visible = showList,
                content = {
                    ItemDrop(
                        frontFon = { viewItem(item) },
                        indexItem = index,
                        heightList = heightList.toFloat(),
                        sizeList = items.size,
                        onMoveItem = onMoveItem,
                        onClickItem = onClickItem,
                    )
                }
            )
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalFoundationApi::class)
@Composable fun ItemDrop(
    indexItem:Int,
    heightList: Float = 0f,
    sizeList: Int = 0,
    frontFon:@Composable () -> Unit,
    onMoveItem: (Int, Int) -> Unit,
    onClickItem: (Int) -> Unit,
){
    val coroutineScope = rememberCoroutineScope()
    val enableDrag = remember { mutableStateOf(false) }
    val stateDrag = StateDragColumn(indexItem = indexItem, heightList = heightList, sizeList = sizeList)
    Box(modifier = Modifier.fillMaxWidth()
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .offset { IntOffset(0, stateDrag.shift.value.roundToInt()) }
                .combinedClickable(
                    onClick = { onClickItem(indexItem) },
                    onLongClick = { enableDrag.value = true },
                )
                .draggable(
                    enabled = enableDrag.value,
                    state = rememberDraggableState { delta ->
                        coroutineScope.launch { stateDrag.shiftItem(delta) } },
                    orientation = Orientation.Vertical,
                    onDragStarted = { stateDrag.onStartDrag() },
                    onDragStopped = {
                        enableDrag.value = false
                        coroutineScope.launch { stateDrag.onStopDrag(onMoveItem) }
                    }
                )
        ){
            frontFon()
        }
    }
}
