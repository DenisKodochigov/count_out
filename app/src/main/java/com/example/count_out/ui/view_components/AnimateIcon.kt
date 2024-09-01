package com.example.count_out.ui.view_components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.count_out.ui.theme.Dimen

@Composable fun AnimateIcon(
    initValue: Dp = Dimen.sizeIcon,
    targetValue: Dp = 17.dp,
    icon: ImageVector = Icons.Default.Bluetooth,
    animate: Boolean = false,
    onClick: ()->Unit = {},
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
    Box(modifier = Modifier.size(Dimen.sizeIcon)){
        Icon(imageVector = icon, contentDescription = "",
            modifier = Modifier.align(Alignment.Center).size(size).clickable { onClick() })
    }
}

@Preview(showBackground = true)
@Composable fun searchBluetoothPreview(){
    AnimateIcon()
}
//    val alpha = remember { Animatable(1f) }
//    LaunchedEffect(PULSE_RATE_MS) { // Restart the effect when the pulse rate changes
//        while (true) {
//            delay(PULSE_RATE_MS) // Pulse the alpha every pulseRateMs to alert the user
//            alpha.animateTo(0f)
//            alpha.animateTo(1f)
//        }
//    }