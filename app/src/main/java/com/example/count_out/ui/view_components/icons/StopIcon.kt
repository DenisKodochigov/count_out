package com.example.count_out.ui.view_components.icons

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.count_out.ui.theme.colors3

@Composable fun StopI( color: Color){
    val size = 39.dp
    val width = 1.dp


    Canvas(Modifier.size(size).background(Color.Transparent)) {
        val sizePx = size .toPx()
        val widthPx = width.toPx()

        drawOval(
            color = color,
            style = Stroke(width = widthPx),
            topLeft = Offset(x = widthPx/2, y = widthPx/2),
            size = Size(sizePx - widthPx, sizePx - widthPx)
        )
        drawRect(
            color = color,
            topLeft = Offset( sizePx/4, sizePx/4),
            size = Size(sizePx/2, sizePx/2),
            style = Stroke(width = widthPx)
        )
    }
}

@Preview
@Composable fun PreviewStopI(){
    StopI(colors3.primary)
}