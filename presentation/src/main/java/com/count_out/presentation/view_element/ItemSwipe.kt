package com.count_out.presentation.view_element

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun ItemSwipe(
    frontView:@Composable () -> Unit,
    actionDragRight:()->Unit,
    actionDragLeft:()->Unit,
    iconLeft: ImageVector = Icons.Default.Edit,
    iconRight: ImageVector = Icons.Default.Delete
){
    val offsetX = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()
    val limitDraggable = 150.dp

    Box(modifier = Modifier.fillMaxWidth()
    ){
        BackFon(iconRight = iconRight,iconLeft = iconLeft, modifier = Modifier.align(alignment = Alignment.Center))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .offset { IntOffset(offsetX.value.roundToInt(), 0) }
                .draggable(
                    state = rememberDraggableState { delta ->
                        coroutineScope.launch { offsetX.snapTo(offsetX.value + delta) } },
                    orientation = Orientation.Horizontal,
                    onDragStopped = {
                        coroutineScope.launch {
                            if (Dp(offsetX.value) > limitDraggable) actionDragRight()
                            if (Dp((-1) * offsetX.value) > limitDraggable) actionDragLeft()
                            offsetX.animateTo(
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
            frontView()
        }
    }
}
@Composable fun BackFon(iconLeft: ImageVector, iconRight: ImageVector, modifier: Modifier = Modifier){
    Row(modifier = modifier.fillMaxWidth(). padding(horizontal = 12.dp)) {
        Icon(imageVector = iconLeft,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(start = 8.dp))
        Spacer(modifier = Modifier.weight(1f))
        Icon(imageVector = iconRight,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(end = 8.dp))
    }
}