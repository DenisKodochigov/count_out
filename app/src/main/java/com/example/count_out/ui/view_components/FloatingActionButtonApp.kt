package com.example.count_out.ui.view_components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.count_out.entity.SizeElement
import com.example.count_out.entity.TagsTesting.FAB_PLUS
import com.example.count_out.ui.theme.sizeApp


@Composable
fun FloatingActionButtonApp( offset: Dp,
                              refreshScreen: MutableState<Boolean>,
                              modifier: Modifier = Modifier,
                              icon: ImageVector, onClick: () -> Unit) {

    val plug = refreshScreen.value

    FloatingActionButton(
        onClick = onClick,
        modifier = modifier
            .offset(0.dp, offset)
            .size(sizeApp(SizeElement.SIZE_FAB))
            .testTag(FAB_PLUS))
    {
        Icon(
            icon, null,
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier
                .padding(sizeApp(SizeElement.PADDING_FAB))
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.primaryContainer)
        )
    }
}
@Composable
fun FabAnimation(show: Boolean, offset: Dp, icon: ImageVector, onClick: () -> Unit) {

    var isAnimated by rememberSaveable { mutableStateOf(false) }
    val refreshScreen = remember{ mutableStateOf(true)}
    val offsetFAB by animateDpAsState(
        targetValue = if (isAnimated) {
            if (show) offset else sizeApp(SizeElement.HEIGHT_FAB_BOX)
        } else {
            if (show) sizeApp(SizeElement.HEIGHT_FAB_BOX) else offset
        },
        animationSpec = tween(durationMillis = 600), label = ""
    )

    FloatingActionButtonApp(offset = offsetFAB,icon = icon, onClick = onClick, refreshScreen = refreshScreen)
    isAnimated = true
}