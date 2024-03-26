package com.example.count_out.ui.view_components.drag_drop_column

//import android.annotation.SuppressLint
//import androidx.compose.animation.AnimatedVisibility
//import androidx.compose.foundation.ExperimentalFoundationApi
//import androidx.compose.foundation.combinedClickable
//import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.offset
//import androidx.compose.foundation.layout.padding
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.MutableState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableIntStateOf
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.input.pointer.pointerInput
//import androidx.compose.ui.layout.onGloballyPositioned
//import androidx.compose.ui.unit.IntOffset
//import androidx.compose.ui.unit.dp
//import com.example.count_out.ui.view_components.lg
//import kotlinx.coroutines.launch
//import kotlin.math.roundToInt
//
//@Composable
//fun <T>ColumnDragDrop(
//    items: List<T>,
//    modifier: Modifier = Modifier,
//    viewItem:@Composable (T) -> Unit,
//    showList: Boolean,
//    onMoveItem: (Int, Int) -> Unit = {_,_->},
//    onClickItem: (Int) -> Unit = {},
//){
//    var heightList by remember { mutableIntStateOf(0) }
//
//    Column( modifier = Modifier
//        .onGloballyPositioned { heightList = it.size.height }
//    ) {
//        items.forEachIndexed { index, item ->
//            AnimatedVisibility(
//                modifier = modifier.padding(top = 8.dp),
//                visible = showList,
//                content = {
//                    ItemDrop(
//                        frontFon = {viewItem( item ) },
//                        indexItem = index,
//                        heightList = heightList.toFloat(),
//                        sizeList = items.size,
//                        onMoveItem = onMoveItem,
//                        onClickItem = onClickItem,
//                    )
//                }
//            )
//        }
//    }
//}
//
//@SuppressLint("UnrememberedMutableState")
//@OptIn(ExperimentalFoundationApi::class)
//@Composable fun ItemDrop(
//    indexItem: Int,
//    heightList: Float = 0f,
//    sizeList: Int = 0,
//    frontFon:@Composable (Modifier) -> Unit,
//    onMoveItem: (Int, Int) -> Unit,
//    onClickItem: (Int) -> Unit,
//){
//    val enableDrag: MutableState<Boolean> = remember { mutableStateOf(false) }
//    val coroutineScope = rememberCoroutineScope()
//    val stateDrag = StateDragColumn(indexItem = indexItem, heightList = heightList, sizeList = sizeList)
//
//    lg("heightList $heightList; indexItem: $indexItem")
//    Box(modifier = Modifier.fillMaxWidth()
//    ){
////        if (enableDrag.value) lg("RowItem $indexItem ")
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .offset { IntOffset(0, stateDrag.shift.value.roundToInt()) }
//                .combinedClickable(
//                    onLongClick = { !enableDrag.value },
//                    onClick = { onClickItem(indexItem) },)
//                .pointerInput(Unit) {
//                    detectDragGesturesAfterLongPress(
//                        onDrag = { change, offset ->
//                            change.consume()
////                            lg("onDrag offsetY: ${offset.y}")
////                            stateDrag.shiftItem(offset.y)
//                            coroutineScope.launch { stateDrag.shiftItem(offset.y) }
//                        },
//                        onDragStart = { stateDrag.onStartDrag() },
//                        onDragEnd = {  },
//                        onDragCancel = {
//                            lg("onDragCancel ")
//                            coroutineScope.launch { stateDrag.onStopDrag(onMoveItem) } }
//                    )
//                }
////                .draggable(
////                    enabled = enableDragLoc.value,
////                    state = rememberDraggableState { delta ->
////                        coroutineScope.launch { stateDrag.shiftItem(delta) } },
////                    orientation = Orientation.Vertical,
////                    onDragStarted = { stateDrag.onStartDrag() },
////                    onDragStopped = {
////                        onLongClick (!enableDragLoc.value)
////                        coroutineScope.launch { stateDrag.onStopDrag(onMoveItem) }
////                    }
////                )
//        ){
//            val modifier = Modifier
////                .composed { Modifier.graphicsLayer {
//////                    lg("graphicsLayer index: $indexItem; ${stateDrag.itemOffset(indexItem)} ")
////                    translationY = stateDrag.itemOffset(indexItem) } }
//            frontFon( modifier)
//        }
//    }
//}