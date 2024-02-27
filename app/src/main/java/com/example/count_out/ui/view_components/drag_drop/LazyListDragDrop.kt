package com.example.count_out.ui.view_components.drag_drop

import androidx.compose.runtime.Composable

@Composable
fun <T>LazyListDragDrop(listItems: List<T>){
    DragDropList(
        items = listItems,
        onMove = { fromIndex, toIndex -> listItems.toMutableList().move(fromIndex, toIndex)}
    )
}