package com.example.count_out.ui.view_components.drag_drop

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun <T>DragDropList(
    items: List<T>,
    onMove: (Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    var overScrollJob by remember { mutableStateOf<Job?>(null) }
    val dragDropState = rememberDragDropState(onMove = onMove)
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()
    val limitDraggable = 10.dp

    LazyColumn(
        modifier = modifier
            .draggable(
                state = rememberDraggableState { delta ->
                    coroutineScope.launch {
                        offsetY.snapTo(offsetY.value + delta)
                        offsetX.snapTo(offsetX.value + delta)
                    }
                },
                orientation = Orientation.Horizontal,
                onDragStopped = {
                    coroutineScope.launch {
                        if (Dp(offsetY.value) > limitDraggable) actionDragUp(Dp(offsetY.value))
                        if (Dp((-1) * offsetY.value) > limitDraggable) actionDragDown(Dp(offsetY.value))
                        offsetY.animateTo(
                            targetValue = 0f,
                            animationSpec = tween(
                                durationMillis = 1000,
                                delayMillis = 0
                            )
                        )
                    }
                }
            )
//            .pointerInput(Unit) {
//                detectDragGesturesAfterLongPress(
//                    onDrag = { change, offset ->
//                        change.consume()
//                        dragDropState.onDrag(offset = offset)
//
//                        if (overScrollJob?.isActive == true)
//                            return@detectDragGesturesAfterLongPress
//
//                        dragDropState.checkForOverScroll().takeIf { it != 0f }?.let {
//                                overScrollJob = scope.launch {
//                                    dragDropState.lazyListState.scrollBy(it)
//                                }
//                            } ?: kotlin.run { overScrollJob?.cancel() }
//                    },
//                    onDragStart = { offset -> dragDropState.onDragStart(offset) },
//                    onDragEnd = { dragDropState.onDragInterrupted() },
//                    onDragCancel = { dragDropState.onDragInterrupted() }
//                )
//            }
            .fillMaxSize()
            .padding(top = 10.dp, start = 10.dp, end = 10.dp),
        state = dragDropState.lazyListState
    ) {
        itemsIndexed(items) { index, item ->
            Column(
                modifier = Modifier
                    .composed {
                        val offsetOrNull = dragDropState.elementDisplacement.takeIf {
                            index == dragDropState.currentIndexOfDraggedItem
                        }
                        Modifier.graphicsLayer {
                            translationY = offsetOrNull ?: 0f
                        }
                    }
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = item.toString(),
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Serif
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

