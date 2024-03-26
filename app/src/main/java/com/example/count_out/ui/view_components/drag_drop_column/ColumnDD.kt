package com.example.count_out.ui.view_components.drag_drop_column

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.count_out.ui.view_components.lg
import kotlinx.coroutines.launch

@Composable
fun <T>ColumnDD(
    items: List<T>,
    modifier: Modifier = Modifier,
    viewItem:@Composable (T) -> Unit,
    showList: Boolean,
    onMoveItem: (Int, Int) -> Unit = {_,_->},
    onClickItem: (Int) -> Unit = {},
){
    var heightList = 0
    Column( modifier = Modifier.onGloballyPositioned { heightList = it.size.height }
    ) {
        items.forEachIndexed { index, item ->
            AnimatedVisibility(
                visible = showList,
                modifier = modifier
                    .padding(top = 8.dp),
                content = {
                    ItemDD(
                        frontFon = { viewItem( item ) },
                        indexItem = index,
                        heightList = heightList,
                        size = items.size,
                        onClickItem = onClickItem,
                        onMoveItem = onMoveItem
                    )
                }
            )
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun ItemDD(
    indexItem: Int,
    heightList: Int = 0,
    size: Int = 0,
    frontFon:@Composable () -> Unit,
    onMoveItem: (Int, Int) -> Unit = {_,_->},
    onClickItem: (Int) -> Unit,
){
    val stateDrag = remember { StateDragColumn( heightList = heightList.toFloat(), sizeList = size) }
    val coroutineScope = rememberCoroutineScope()
    lg("heightList $heightList, stateDrag $stateDrag")

    Box(modifier = Modifier.fillMaxWidth()
    ){
//        if (enableDrag.value) lg("RowItem $indexItem ")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .offset { IntOffset(0, stateDrag.itemOffset()) }
                .clickable { onClickItem(indexItem) }
                .pointerInput(Unit) {
                    detectDragGesturesAfterLongPress(
                        onDrag = { change, offset ->
                            change.consume()
                            coroutineScope.launch { stateDrag.shiftItem(offset.y) }
                        },
                        onDragStart = { stateDrag.onStartDrag(indexItem) },
                        onDragEnd = { },
                        onDragCancel = {
                            coroutineScope.launch { stateDrag.onStopDrag(indexItem, onMoveItem) }
                        }
                    )
                },
        ){
            frontFon( )
        }
    }
}