package com.example.count_out.ui.view_components.drag_drop_column.column

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ListColumnDragDopItem() {
    val items = remember { mutableStateOf(ReorderItem) }
    ColumnDD(
        items = items,
        onMoveItem = { from, to -> items.value = move (from, to, items.value)},
        viewItem = { item -> Element(item) }
    )
}

@Composable
fun Element(item:String){
    Column( modifier = Modifier
        .background(color = Color.Gray, shape = RoundedCornerShape(8.dp))
        .fillMaxWidth()
        .padding(12.dp),
        content = { Text(text = item, fontSize = 20.sp) }
    )
    Spacer(modifier = Modifier.height(12.dp))
}

var ReorderItem = listOf("Item 0", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6" )

fun <T>move(from: Int, to: Int, list: List<T>): List<T> {
    if (from == to) return list
    val list_ = list.toMutableList()
    val element = list_.removeAt(from) ?: return list
    list_.add(to, element)
    return list_
}