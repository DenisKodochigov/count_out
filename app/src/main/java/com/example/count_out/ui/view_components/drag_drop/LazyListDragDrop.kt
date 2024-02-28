package com.example.count_out.ui.view_components.drag_drop

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.count_out.ui.theme.interReg12
import com.example.count_out.ui.theme.shapesApp
import com.example.count_out.ui.view_components.TextApp
import com.example.count_out.ui.view_components.log

@Composable
fun <T>LazyListDragDrop(listItems: List<T>){
    DragDropLazyList(items = listItems)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T>DragDropLazyList(
    items: List<T>,
    modifier: Modifier = Modifier
) {
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()
    val lazyState = rememberLazyListState()
    val limitDraggable = 150.dp

    LazyColumn(
        state = lazyState,
        modifier = modifier
            .fillMaxSize()
            .padding(top = 10.dp, start = 10.dp, end = 10.dp),
    ) {
        itemsIndexed(items) { index, item ->
            ItemDrop(
                frontFon = { ElementColumBasket(item, modifier = Modifier.animateItemPlacement()) },
                actionDragLeft = { actionDragUp(it)},
                actionDragRight = {actionDragUp(it) },
            )
            Spacer(modifier = Modifier.height(4.dp))
//            Row(
//                modifier = Modifier
//                    .background(Color.Gray, shape = RoundedCornerShape(8.dp))
//                    .fillMaxWidth()
//                    .padding(20.dp)
//                    .offset { IntOffset(0, offsetY.value.roundToInt()) }
//                    .draggable(
//                        state = rememberDraggableState { delta ->
//                            coroutineScope.launch {
//                                offsetY.snapTo(offsetY.value + delta)
//                                offsetX.snapTo(offsetX.value + delta)
//                            }
//                        },
//                        orientation = Orientation.Vertical,
//                        onDragStopped = {
//                            coroutineScope.launch {
//                                if (Dp(offsetY.value) > limitDraggable) actionDragUp(Dp(offsetY.value))
//                                if (Dp((-1) * offsetY.value) > limitDraggable) actionDragDown(
//                                    Dp(offsetY.value)
//                                )
//                                offsetY.animateTo(
//                                    targetValue = 0f,
//                                    animationSpec = tween(durationMillis = 1000, delayMillis = 0)
//                                )
//                            }
//                        }
//                    )
//            ) {
//                Row(
//                    modifier = Modifier
//                        .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
//                        .fillMaxWidth())
//                {
//                    Text(
//                        text = item.toString(),
//                        fontSize = 16.sp,
//                        fontFamily = FontFamily.Serif
//                    )
//                }
//            }
//            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun <T>ElementColumBasket (item:T, modifier: Modifier){
    Row(
        modifier = modifier
            .clip(shape = shapesApp.extraSmall)
            .heightIn(min = 24.dp, max = 56.dp)
            .fillMaxWidth()
            .background(color = Color.Gray)
            .padding(horizontal = 8.dp, vertical = 12.dp)
            .clickable { log(true, " onClick item") },
        verticalAlignment = Alignment.CenterVertically
    ){
        TextApp(
            text = item.toString(),
            textAlign = TextAlign.Left,
            style = interReg12,
            modifier = modifier.weight(1f)
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}
