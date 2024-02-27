package com.example.count_out.ui.view_components.drag_drop

import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState

fun LazyListState.getVisibleItemInfoFor(absolute: Int): LazyListItemInfo?{
    return this.layoutInfo.visibleItemsInfo
        .getOrNull(absolute - this.layoutInfo.visibleItemsInfo.first().index)
}
fun <T> MutableList<T>.move(from:Int, to:Int){
    if (from != to){
        val element = this.removeAt(from) ?: return
        this.add(to, element)
    }
}