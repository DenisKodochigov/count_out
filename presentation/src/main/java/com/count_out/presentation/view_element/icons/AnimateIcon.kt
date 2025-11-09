package com.count_out.presentation.view_element.icons

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
import androidx.compose.material3.MaterialTheme.colorScheme
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.count_out.presentation.models.Dimen.sizeIcon

@Composable
fun AnimateIcon(
    initValue: Dp = sizeIcon,
    targetValue: Dp = 22.dp,
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
    Box(modifier = Modifier.size( sizeIcon)){
        Icon( imageVector = icon, contentDescription = "", tint = colorScheme.outline,
            modifier = Modifier.align(Alignment.Center).size(size).clickable { onClick() })
    }
}