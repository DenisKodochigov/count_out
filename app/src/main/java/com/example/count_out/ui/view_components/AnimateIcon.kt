package com.example.count_out.ui.view_components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable fun AnimateIcon(
    initValue: Dp = 30.dp,
    targetValue: Dp = 17.dp,
    icon: ImageVector = Icons.Default.Bluetooth,
    animate: Boolean = false
){
    var target by remember { mutableStateOf(initValue) }
    var iteration by remember { mutableIntStateOf(1) }
    LaunchedEffect(animate) {
        iteration = if (animate) 1000 else 1
        target = if (animate) if (target == initValue) targetValue else initValue else initValue
    }
    val size by animateDpAsState(
        label = "size",
        targetValue = target,
        animationSpec = repeatable(
            iterations = iteration,
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        )
    )
    Icon(imageVector = icon, contentDescription = "", modifier = Modifier.size(size))
}

@Preview(showBackground = true)
@Composable fun searchBluetoothPreview(){
    AnimateIcon()
}
