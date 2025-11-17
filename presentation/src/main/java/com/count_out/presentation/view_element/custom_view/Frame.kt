package com.count_out.presentation.view_element.custom_view

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Composable fun Frame(
    colorAlpha: Float = 1f,
    colorBorder: Color = MaterialTheme.colorScheme.surfaceBright,
    background: Color = MaterialTheme.colorScheme.surfaceContainerHighest,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp, bottomStart = 8.dp, bottomEnd = 8.dp ),
    contour: PaddingValues = PaddingValues(top = 1.dp, start = 1.dp, end = 1.dp, bottom = 1.dp),
    content:@Composable ()->Unit)
{
    val color = with(colorBorder){ Color(red, green, blue, alpha * colorAlpha) }
    Box( content = { content() },
        modifier = modifier
            .animateContentSize()
            .background(color, shape = shape)
            .padding(contour)// отступ от границ фрагмента
            .background( color = background ))
}
@Composable fun FrameBackground(
    colorAlpha: Float = 1f,
    colorBorder: Color = MaterialTheme.colorScheme.surfaceBright,
    background: Color = MaterialTheme.colorScheme.background,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp, bottomStart = 8.dp, bottomEnd = 8.dp ),
    contour: PaddingValues = PaddingValues(top = 1.dp, start = 1.dp, end = 1.dp, bottom = 1.dp),
    content:@Composable ()->Unit)
{
    Frame(
        colorAlpha = colorAlpha,
        colorBorder = colorBorder,
        background = background,
        modifier = modifier,
        shape = shape,
        contour = contour,
        content = content
    )
}



