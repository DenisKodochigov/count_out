package com.example.count_out.ui.view_components.drag_drop_column.column

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.zIndex
import com.example.count_out.ui.view_components.lg

@SuppressLint("UnnecessaryComposedModifier")
@Composable
fun <T>ColumnDD(
    items: MutableState<List<T>>,
    modifier: Modifier = Modifier,
    viewItem:@Composable (T) -> Unit,
    showList: Boolean = true,
    onMoveItem: (Int, Int) -> Unit = {_,_->},
    onClickItem: (Int) -> Unit = {},
){
    val heightList: MutableState<Int> = remember { mutableIntStateOf(0) }
    val stateDrag = remember { StateDragColumn(heightList = heightList, sizeList = items.value.count()) }
    AnimatedVisibility(
        visible = showList,
        content = {
            Column(modifier = modifier
                .onGloballyPositioned { heightList.value = it.size.height }
                .pointerInput(Unit) {
                    detectDragGesturesAfterLongPress(
                        onDragStart = { offset -> stateDrag.onStartDrag(offset.y) },
                        onDrag = { change, offset ->
                            change.consume()
                            stateDrag.onDrag(offset.y) },
                        onDragEnd = { stateDrag.onStopDrag(onMoveItem) },
                        onDragCancel = {}
                    )
                }){
                lg(" ColumnDD ${items.value}")
                items.value.forEachIndexed { index, item ->
                    val offset = stateDrag.itemOffset(index)
                    Column(
                        modifier = Modifier
                            .clickable { onClickItem(index) }
                            .zIndex(stateDrag.itemZ(index))
                            .offset { IntOffset(0, offset ) },
                        content = { viewItem(item) }
                    )
                }
            }
        }
    )
}
