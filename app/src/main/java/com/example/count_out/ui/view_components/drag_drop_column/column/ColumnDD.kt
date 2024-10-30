package com.example.count_out.ui.view_components.drag_drop_column.column

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.zIndex

@SuppressLint("UnnecessaryComposedModifier")
@Composable
fun <T>ColumnDD(
    items: List<T>,
    modifier: Modifier = Modifier,
    content:@Composable (T) -> Unit,
    showList: Boolean = true,
    onMoveItem: (Int, Int) -> Unit = {_,_->},
    onClickItem: (Int) -> Unit = {},
){
    val stateDrag = remember { StateDDColumn(sizeList = items.count()) }

    AnimatedVisibility(
        visible = showList,
        content = {
            Column(modifier = modifier
                .onGloballyPositioned { stateDrag.heightList.value = it.size.height }
                .pointerInput(Unit) {
                    detectDragGesturesAfterLongPress(
                        onDragStart = { offset -> stateDrag.onStartDrag(offset.y) },
                        onDrag = { change, offset ->
                            change.consume()
                            stateDrag.onDrag(offset.y)
                        },
                        onDragEnd = { stateDrag.onStopDrag(onMoveItem) },
                        onDragCancel = {}
                    )
                }){
                items.forEachIndexed { index, item ->
                    val offsetIt = stateDrag.offsetIt(index) ///Не переносить в modifier.
                    val itemZ = stateDrag.itemZ(index)
                    Column(
                        modifier = Modifier
                            .onGloballyPositioned { stateDrag.heightItem.value = it.size.height }
                            .clickable { onClickItem(index) }
                            .zIndex( itemZ )
                            .offset { IntOffset(0, offsetIt ) },
                        content = { content(item) }
                    )
                }
            }
        }
    )
}

