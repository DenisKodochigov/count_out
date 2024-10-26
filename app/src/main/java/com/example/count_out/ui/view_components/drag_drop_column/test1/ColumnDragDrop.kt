package com.example.count_out.ui.view_components.drag_drop_column.test1

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun <T>ColumnDD1(
    items: List<T>,
    modifier: Modifier = Modifier,
    showList: Boolean = true,
    onMoveItem: (Int, Int) -> Unit,
    onClickItem: (Int) -> Unit = {},
    viewItem: @Composable (T)->Unit,
) {
    val lazyListState: LazyListState = rememberLazyListState()
    val stateDD = remember { StateDD(lazyListState = lazyListState, onMove = onMoveItem) }

    if (!showList) return
    LazyColumn(
        state = lazyListState,
        modifier = modifier
            .fillMaxSize()
            .padding(top = 10.dp, start = 10.dp, end = 10.dp)
            .pointerInput(Unit) {
                detectDragGesturesAfterLongPress(
                    onDrag = { change, offset ->
                        change.consume()
                        stateDD.onDrag(offset = offset) },
                    onDragStart = { offset -> stateDD.onDragStart(offset) },
                    onDragEnd = { stateDD.onDragInterrupted() },
                    onDragCancel = { stateDD.onDragInterrupted() }
                )
            },
    ) {
        itemsIndexed(items) { index, item ->
            Column(
                modifier = Modifier
                    .clickable { onClickItem(index) }
                    .zIndex(stateDD.zIndex(index))
                    .graphicsLayer{ translationY = stateDD.offsetIt(index) ?: 0f } ,
                content = { viewItem(item)}
            )
        }
    }
}