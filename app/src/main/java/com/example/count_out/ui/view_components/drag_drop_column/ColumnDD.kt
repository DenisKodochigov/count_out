package com.example.count_out.ui.view_components.drag_drop_column

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.count_out.domain.vibrate

@Composable
fun <T>ColumnDD(
    items: List<T>,
    modifier: Modifier = Modifier,
    viewItem:@Composable (T) -> Unit,
    showList: Boolean,
    onMoveItem: (Int, Int) -> Unit = {_,_->},
    onClickItem: (Int) -> Unit = {},
){
    val heightList: MutableState<Int> = remember { mutableIntStateOf(0) }

    AnimatedVisibility(
        visible = showList,
        content = {
            Column(modifier = modifier.onGloballyPositioned { heightList.value = it.size.height }){
                items.forEachIndexed { index, item ->
                    ItemDD(
                        frontFon = { viewItem (item) },
                        indexItem = index,
                        heightList = heightList,
                        size = items.size,
                        onClickItem = onClickItem,
                        onMoveItem = onMoveItem
                    )
                }
            }
        }
    )
}

@SuppressLint("UnrememberedMutableState", "UnnecessaryComposedModifier")
@Composable
fun ItemDD(
    indexItem: Int,
    heightList: MutableState<Int>,
    size: Int = 0,
    frontFon:@Composable () -> Unit,
    onMoveItem: (Int, Int) -> Unit = {_,_->},
    onClickItem: (Int) -> Unit,
){
    val stateDrag = remember { StateDragColumn(heightList = heightList, sizeList = size) }
    val context = LocalContext.current
    val verticalTranslation by animateIntAsState(targetValue = stateDrag.itemOffset(), label = "")

    Box(modifier = Modifier.fillMaxWidth().padding(top = 6.dp)
        .offset { IntOffset(0, verticalTranslation) }
//        .graphicsLayer { translationY = stateDrag.itemOffset().toFloat() }
        .zIndex(stateDrag.itemZ())
        .clickable { onClickItem(indexItem) }
        .pointerInput(Unit) {
//            detectDragGesturesAfterLongPress(
//                onDrag = { change, offset ->
//                    change.consume()
//                    stateDrag.shiftItem(offset.y)
//                },
//                onDragStart = {
//                    vibrate(context)
//                    stateDrag.onStartDrag(indexItem) },
//                onDragEnd = { stateDrag.onStopDrag(indexItem, onMoveItem) },
//                onDragCancel = {}
//            )
            detectDragGesturesAfterLongPress(
                onDragStart = {
                    vibrate(context)
                    stateDrag.onStartDrag(indexItem) },
                onDrag = { change, offset ->
                    change.consume()  //?
                    stateDrag.shiftItem(offset.y,onMoveItem)
                },
                onDragEnd = { stateDrag.onStopDrag() },
                onDragCancel = {}
            )
        },
    ){
        frontFon()
    }
}