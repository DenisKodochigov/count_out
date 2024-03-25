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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.count_out.ui.view_components.lg
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun <T>ColumnDD(
    items: List<T>,
    modifier: Modifier = Modifier,
    viewItem:@Composable (T) -> Unit,
    showList: Boolean,
    onMoveItem: (Int, Int) -> Unit = {_,_->},
    onClickItem: (Int) -> Unit = {},
){
    var heightList by remember { mutableIntStateOf(0) }
    val stateDrag = StateDragColumn( )
    val coroutineScope = rememberCoroutineScope()

    Column( modifier = Modifier
        .onGloballyPositioned { heightList = it.size.height }
    ) {
        stateDrag.heightList = heightList.toFloat()
        stateDrag.sizeList = items.size
        items.forEachIndexed { index, item ->
            AnimatedVisibility(
                visible = showList,
                modifier = modifier
                    .padding(top = 8.dp)
                    .pointerInput(Unit) {
                        detectDragGesturesAfterLongPress(
                            onDrag = { change, offset ->
                                change.consume()
//                            lg("onDrag offsetY: ${offset.y}")
//                            stateDrag.shiftItem(offset.y)
                                coroutineScope.launch { stateDrag.shiftItem(offset.y, index) }
                            },
                            onDragStart = { stateDrag.onStartDrag(index) },
                            onDragEnd = {  },
                            onDragCancel = {
                                lg("onDragCancel ")
                                coroutineScope.launch { stateDrag.onStopDrag(index,onMoveItem) } }
                        )
                    },
                content = {
                    ItemDD(
                        frontFon = { viewItem( item ) },
                        indexItem = index,
                        offsetY = stateDrag.shift.value.roundToInt(),
                        onClickItem = onClickItem,
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
    offsetY: Int,
    frontFon:@Composable () -> Unit,
    onClickItem: (Int) -> Unit,
){
    Box(modifier = Modifier.fillMaxWidth()
    ){
//        if (enableDrag.value) lg("RowItem $indexItem ")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .offset { IntOffset(0, offsetY) }
                .clickable { onClickItem(indexItem) }
        ){
            frontFon( )
        }
    }
}