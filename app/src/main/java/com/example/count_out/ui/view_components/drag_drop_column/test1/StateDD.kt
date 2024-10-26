package com.example.count_out.ui.view_components.drag_drop_column.test1

import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset

class StateDD(val lazyListState: LazyListState, private val onMove: (Int, Int) -> Unit
) {
    private var draggedDistance by mutableFloatStateOf(0f)
    private var draggedItem by mutableStateOf<LazyListItemInfo?>(null)
    private var indexItemUnder by mutableStateOf<Int?>(null)
    private val ItemUnder: LazyListItemInfo?
        get() = indexItemUnder?.let { lazyListState.getVisibleItemInfoFor(absolute = it) }

    fun onDragStart(offset: Offset) {
        lazyListState.layoutInfo.visibleItemsInfo.firstOrNull { itemInfo ->
            offset.y.toInt() in itemInfo.offset..(itemInfo.offset + itemInfo.size) }?.also { itemIn ->
            indexItemUnder = itemIn.index
            draggedItem = itemIn
        }
    }

    fun onDragInterrupted() {
        draggedDistance = 0f
        indexItemUnder = null
        draggedItem = null
    }

    fun onDrag(offset: Offset) {
        draggedDistance += offset.y
        draggedItem?.let{ element->
            val startOffset = element.offset + draggedDistance
            val endOffset = element.offsetEnd + draggedDistance

            ItemUnder?.let { itemUnder ->
                lazyListState.layoutInfo.visibleItemsInfo
                    .filterNot { item ->
                        item.offsetEnd < startOffset || item.offset > endOffset || itemUnder.index == item.index }
                    .firstOrNull { item ->
                        when {
                            startOffset - itemUnder.offset > 0 -> (endOffset > item.offsetEnd)
                            else -> (startOffset < item.offset) }
                    }?.also { item ->
                        indexItemUnder?.let { current -> onMove.invoke(current, item.index) }
                        indexItemUnder = item.index
                    }
            }
        }
    }
    fun offsetIt(index: Int): Float? = ItemUnder?.let { item ->
        (draggedItem?.offset ?: 0f).toFloat() + draggedDistance - item.offset }
        .takeIf { index == indexItemUnder }
    fun zIndex(index: Int) = if (index == indexItemUnder ) 1f else 0f
}