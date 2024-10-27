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
import com.example.count_out.ui.view_components.lg

@Composable
fun ListColumnDragDopItem() {
    val items = remember { mutableStateOf(ReorderItem) }
    ColumnDD(
        items = items,
        onMoveItem = { fromIndex, toIndex -> ReorderItem.toMutableList().move(fromIndex, toIndex) },
        viewItem = { item -> Element(item) }
    )
}

@Composable
fun Element(item:String){
    Column( modifier = Modifier
        .background(color = Color.Gray, shape = RoundedCornerShape(8.dp))
        .fillMaxWidth()
        .padding(12.dp),
        content = { Text(text = item, fontSize = 16.sp) }
    )
    Spacer(modifier = Modifier.height(12.dp))
}

val ReorderItem = listOf("Item 0", "Item 1", "Item 2", "Item 3", )
//val ReorderItem = mutableListOf(
//    "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7", "Item 8", "Item 9",
//    "Item 10", "Item 11", "Item 12", "Item 13", "Item 14", "Item 15", "Item 16", "Item 17",
//    "Item 18", "Item 19", "Item 20"
//)
fun <T> MutableList<T>.move(from: Int, to: Int) {
    lg(" move $from $to")
    if (from == to) return
    val element = this.removeAt(from) ?: return
    this.add(to, element)
}