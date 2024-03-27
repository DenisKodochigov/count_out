package com.example.count_out.ui.view_components.drag_drop_column

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
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
import javax.inject.Singleton
import kotlin.math.round
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
@Composable
fun ItemDrop(
    indexItem:Int,
    heightList: Float = 0f,
    sizeList: Int = 0,
    frontFon:@Composable () -> Unit,
    onMoveItem: (Int, Int) -> Unit,
    onClickItem: (Int) -> Unit,
){
    val coroutineScope = rememberCoroutineScope()
    val enableDrag = remember { mutableStateOf(false) }
    val stateDrag = StateDragDropColumn(indexItem = indexItem, heightList = heightList, sizeList = sizeList)
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
@Singleton
data class StateDragDropColumn(
    val indexItem: Int = 0,
    var offset: Float = 0f,
    val heightList: Float = 1f,
    val sizeList: Int = 0,
    var shift: Animatable<Float, AnimationVector1D> = Animatable(0f),
    var maxOffsetUp: Float = 0f,
    var maxOffsetDown: Float = 0f,
){
    private val heightItem = if (heightList > 0 && sizeList > 0) (heightList / sizeList) else 0f
    fun onStartDrag() {
        if (heightList > 0 && sizeList > 0) {
            offset = indexItem * heightItem
            maxOffsetDown = heightList
        } else {
//            log(true, "onStartDrag heightList:$heightList; sizeList:$sizeList")
        }
//        log(true, "onStartDrag heightList:$heightList; sizeList:$sizeList")
    }
    fun onStopDrag(onMoveItem: (Int, Int) -> Unit){
        val moveToIndex = indexItem + round(shift.value / heightItem).toInt()
        onMoveItem( indexItem, moveToIndex )
    }
    suspend fun shiftItem( delta: Float){
        if ((offset + shift.value + delta) in maxOffsetUp..maxOffsetDown) {
            shift.snapTo(shift.value + delta)
            shift.value.plus(delta)
        }
//        log(true, "offset: $offset; shift.value: ${shift.value}; shiftItem: $delta; ")
    }
}
