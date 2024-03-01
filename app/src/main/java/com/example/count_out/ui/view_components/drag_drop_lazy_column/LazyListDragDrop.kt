package com.example.count_out.ui.view_components.drag_drop_lazy_column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.count_out.ui.view_components.log
@Composable
fun <T>LazyListDragDrop(
    items: MutableState<List<T>>,
    modifier: Modifier = Modifier,
    viewItem:@Composable (T) -> Unit
) {
    val lazyState = rememberLazyListState()

    LazyColumn(
        state = lazyState,
        modifier = modifier.fillMaxSize(),
    ) {
        itemsIndexed(items.value) { index, item ->
            ItemDrop(
                lazyState = lazyState,
                indexItem = index,
                frontFon = { viewItem(item) },
                onClickItem = {},
                onMoveItem = { indexTo ->
                    items.value = moveItem(list = items.value.toMutableList(), from = index, to = indexTo)
//                    items.value.toMutableList().move(index, indexTo)
                },
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

fun <T>moveItem(list:MutableList<T>, from: Int, to: Int): List<T> {
    if (from != to){
        val element = list.removeAt(from)
        list.add(to, element)
    }
    return list
}

fun <T> MutableList<T>.move(from:Int, to:Int){
    if (from != to){
        val element = this.removeAt(from) ?: return
        this.add(to, element)
    }
    this.forEach {item-> log(true, "item: $item") }
}


