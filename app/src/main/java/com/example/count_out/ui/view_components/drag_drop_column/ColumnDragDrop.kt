package com.example.count_out.ui.view_components.drag_drop_column

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.count_out.ui.view_components.lg
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun <T>ColumnDragDrop(
    items: List<T>,
    modifier: Modifier = Modifier,
    enableDrag: MutableState<Boolean>,
    viewItem:@Composable (T) -> Unit,
    showList: Boolean,
    onMoveItem: (Int, Int) -> Unit = {_,_->},
    onClickItem: (Int) -> Unit = {},
    onLongClick: (Boolean)-> Unit ={},
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
                        enableDrag = enableDrag,
                        sizeList = items.size,
                        onMoveItem = onMoveItem,
                        onClickItem = onClickItem,
                        onLongClick = onLongClick,
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
    enableDrag: MutableState<Boolean>,
    frontFon:@Composable () -> Unit,
    onMoveItem: (Int, Int) -> Unit,
    onClickItem: (Int) -> Unit,
    onLongClick: (Boolean)-> Unit,
){
    val enableDragLoc: MutableState<Boolean> = remember { mutableStateOf(enableDrag.value) }
    val coroutineScope = rememberCoroutineScope()
    val stateDrag = StateDragColumn(indexItem = indexItem, heightList = heightList, sizeList = sizeList)

    Box(modifier = Modifier.fillMaxWidth()
    ){
        if (enableDrag.value) lg("RowItem $indexItem ")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .offset { IntOffset(0, stateDrag.shift.value.roundToInt()) }
                .combinedClickable(
                    onLongClick = { onLongClick (!enableDragLoc.value) },
                    onClick = { onClickItem(indexItem) },)
                .pointerInput(Unit) {
                    detectDragGesturesAfterLongPress(
                        onDrag = { change, offset ->
                            change.consume()
                            lg("onDrag offsetY: ${offset.y}")
                            coroutineScope.launch { stateDrag.shiftItem(offset.y) }
                        },
                        onDragStart = { stateDrag.onStartDrag() },
                        onDragEnd = {  },
                        onDragCancel = {
                            lg("onDragCancel ")
                            coroutineScope.launch { stateDrag.onStopDrag(onMoveItem) } }
                    )
                }
//                .draggable(
//                    enabled = enableDragLoc.value,
//                    state = rememberDraggableState { delta ->
//                        coroutineScope.launch { stateDrag.shiftItem(delta) } },
//                    orientation = Orientation.Vertical,
//                    onDragStarted = { stateDrag.onStartDrag() },
//                    onDragStopped = {
//                        onLongClick (!enableDragLoc.value)
//                        coroutineScope.launch { stateDrag.onStopDrag(onMoveItem) }
//                    }
//                )
        ){
            frontFon()
        }
    }
}

//############################################################################################

data class ReorderItem(
    val id: Int = 0
)

@SuppressLint("UnnecessaryComposedModifier")
@Composable
fun DragDropList(
    items: List<ReorderItem>,
    onMove: (Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    var overscrollJob by remember { mutableStateOf<Job?>(null) }
    val dragDropListState = rememberDragDropListState(onMove = onMove)

    LazyColumn(
        modifier = modifier
            .pointerInput(Unit) {
                detectDragGesturesAfterLongPress(
                    onDrag = { change, offset ->
                        change.consume()
                        dragDropListState.onDrag(offset)
                        if (overscrollJob?.isActive == true)
                            return@detectDragGesturesAfterLongPress
                        dragDropListState.checkForOverScroll()
                            .takeIf { it != 0f }
                            ?.let { overscrollJob = scope.launch { dragDropListState.lazyListState.scrollBy(it) } }
                            ?: run { overscrollJob?.cancel() }
                    },
                    onDragStart = { offset -> dragDropListState.onDragStart(offset) },
                    onDragEnd = { dragDropListState.onDragInterrupted() },
                    onDragCancel = { dragDropListState.onDragInterrupted() }
                )
            },
        state = dragDropListState.lazyListState
    ) {
        itemsIndexed(items) { index, item ->
            Column(
                modifier = Modifier
                    .composed {
                        val offsetOrNull = dragDropListState.elementDisplacement.takeIf {
                                index == dragDropListState.currentIndexOfDraggedItem }
                        Modifier
                            .graphicsLayer { translationY = offsetOrNull ?: 0f }
                    }
                    .background(color = Color.White, shape = RoundedCornerShape(4.dp))
                    .fillMaxWidth()
            ) { Text(text = "Item ${item.id}") }
        }
    }
}

@Composable
fun rememberDragDropListState(
    lazyListState: LazyListState = rememberLazyListState(),
    onMove: (Int, Int) -> Unit,
):DragDropListState {
    return remember { DragDropListState(lazyListState = lazyListState, onMove = onMove) }
}

class DragDropListState(
    val lazyListState: LazyListState,
    private val onMove: (Int, Int) -> Unit
) {
    private var draggedDistance by mutableFloatStateOf(0f)

    // used to obtain initial offsets on drag start
    var initiallyDraggedElement by mutableStateOf<LazyListItemInfo?>(null)
    var currentIndexOfDraggedItem by mutableStateOf<Int?>(null)
    private val initialOffsets: Pair<Int, Int>?
        get() = initiallyDraggedElement?.let { Pair(it.offset, it.offsetEnd) }

    val elementDisplacement: Float?
        get() = currentIndexOfDraggedItem
            ?.let { lazyListState.getVisibleItemInfoFor(absoluteIndex = it) }
            ?.let { item ->
                (initiallyDraggedElement?.offset ?: 0f).toFloat() + draggedDistance - item.offset
            }

    private val currentElement: LazyListItemInfo?
        get() = currentIndexOfDraggedItem?.let { lazyListState.getVisibleItemInfoFor(absoluteIndex = it) }

    private var overscrollJob by mutableStateOf<Job?>(null)

    fun onDragStart(offset: Offset) {
        lazyListState.layoutInfo.visibleItemsInfo
            .firstOrNull { item -> offset.y.toInt() in item.offset..(item.offset + item.size) }
            ?.also {
                currentIndexOfDraggedItem = it.index
                initiallyDraggedElement = it
            }
    }

    fun onDragInterrupted() {
        draggedDistance = 0f
        currentIndexOfDraggedItem = null
        initiallyDraggedElement = null
        overscrollJob?.cancel()
    }

    fun onDrag(offset: Offset) {
        draggedDistance += offset.y

        initialOffsets?.let { (topOffset, bottomOffset) ->
            val startOffset = topOffset + draggedDistance
            val endOffset = bottomOffset + draggedDistance

            currentElement?.let { hovered ->
                lazyListState.layoutInfo.visibleItemsInfo
                    .filterNot { item -> item.offsetEnd < startOffset || item.offset > endOffset || hovered.index == item.index }
                    .firstOrNull { item ->
                        val delta = startOffset - hovered.offset
                        when {
                            delta > 0 -> (endOffset > item.offsetEnd)
                            else -> (startOffset < item.offset)
                        }
                    }
                    ?.also { item ->
                        currentIndexOfDraggedItem?.let { current -> onMove.invoke(current, item.index) }
                        currentIndexOfDraggedItem = item.index
                    }
            }
        }
    }

    fun checkForOverScroll(): Float {
        return initiallyDraggedElement?.let {
            val startOffset = it.offset + draggedDistance
            val endOffset = it.offsetEnd + draggedDistance

            return@let when {
                draggedDistance > 0 -> (endOffset - lazyListState.layoutInfo.viewportEndOffset).takeIf { diff -> diff > 0 }
                draggedDistance < 0 -> (startOffset - lazyListState.layoutInfo.viewportStartOffset).takeIf { diff -> diff < 0 }
                else -> null
            }
        } ?: 0f
    }

}

/*
    LazyListItemInfo.index is the item's absolute index in the list
    Based on the item's "relative position" with the "currently top" visible item,
    this returns LazyListItemInfo corresponding to it
*/
fun LazyListState.getVisibleItemInfoFor(absoluteIndex: Int): LazyListItemInfo? {
    return this.layoutInfo.visibleItemsInfo.getOrNull(absoluteIndex - this.layoutInfo.visibleItemsInfo.first().index)
}

/*Bottom offset of the element in Vertical list*/
val LazyListItemInfo.offsetEnd: Int
    get() = this.offset + this.size

/*  Moving element in the list*/
fun <T> MutableList<T>.move(from: Int, to: Int) {
    if (from == to) return
    val element = this.removeAt(from) ?: return
    this.add(to, element)
}