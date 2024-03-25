package com.example.count_out.ui.view_components.dragdrop1

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

data class Itm(
    val id: Int = 0,
    val message: String,
)
@SuppressLint("UnnecessaryComposedModifier")
@Composable
fun DragDropList(
    items1: List<Itm> = emptyList(),
    onMove: (Int, Int) -> Unit = {_,_->},
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    var overscrollJob by remember { mutableStateOf<Job?>(null) }
    val dragDropListState = rememberDragDropListState(onMove = onMove)
    var i = 0
    val items = listOf(Itm(i++, " item $i"), Itm(i++, " item $i"),
        Itm(i++, " item $i"),Itm(i++, " item $i"),Itm(i++, " item $i"),)
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
): DragDropListState {
    return remember { DragDropListState(lazyListState = lazyListState, onMove = onMove) }
}
