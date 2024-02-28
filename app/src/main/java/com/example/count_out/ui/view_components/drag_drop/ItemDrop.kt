package com.example.count_out.ui.view_components.drag_drop

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.count_out.ui.view_components.log
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun ItemDrop(
    frontFon:@Composable () -> Unit,
    actionDragRight:(Dp)->Unit,
    actionDragLeft:(Dp)->Unit,
){
    val offset = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()
    val limitDraggable = 15.dp

    Box(modifier = Modifier.fillMaxWidth()
    ){
        BackFon( modifier = Modifier.align(alignment = Alignment.Center))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .offset { IntOffset(0, offset.value.roundToInt()) }
                .draggable(
                    state = rememberDraggableState { delta ->
                        coroutineScope.launch { offset.snapTo(offset.value + delta) }
                    },
                    orientation = Orientation.Vertical,
                    onDragStopped = {
                        coroutineScope.launch {
                            if (Dp(offset.value) > limitDraggable) actionDragRight(Dp(offset.value))
                            if (Dp((-1) * offset.value) > limitDraggable) actionDragLeft(Dp(offset.value))
                            offset.animateTo(
                                targetValue = 0f,
                                animationSpec = tween(
                                    durationMillis = 1000,
                                    delayMillis = 0
                                )
                            )
                        }
                    }
                )
        ) {
            frontFon()
        }
    }
}
@Composable
fun BackFon(modifier: Modifier = Modifier){
    Row(modifier = modifier.fillMaxWidth(). padding(horizontal = 12.dp)) {
        Spacer(modifier = Modifier.weight(1f))
    }
}
fun actionDragUp(offsetY: Dp){
    log(true, "actionDragUp offsetY: $offsetY")
}

fun actionDragDown(offsetY: Dp){
    log(true, "actionDragDown offsetY: $offsetY")
}