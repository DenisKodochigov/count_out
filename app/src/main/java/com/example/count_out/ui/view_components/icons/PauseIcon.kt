package com.example.count_out.ui.view_components.icons

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable fun PauseIcon(color: Color){
    val size = 39.dp
    val width = 1.dp
    Spacer(modifier = Modifier.size(size)
        .drawWithCache {
            val sizePx = size .toPx()
            val widthPx = width.toPx()
            onDrawWithContent {
                drawOval(
                    color = color,
                    style = Stroke(width = widthPx),
                    topLeft = Offset(x = widthPx/2, y = widthPx/2),
                    size = Size(sizePx - widthPx, sizePx - widthPx)
                )
                drawLine(
                    color = color,
                    strokeWidth = widthPx*2,
                    start = Offset(sizePx * 3/8,sizePx/4),
                    end = Offset(sizePx * 3/8, sizePx * 3/4))
                drawLine(
                    color = color,
                    strokeWidth = widthPx*2,
                    start = Offset(sizePx * 5/8,sizePx/4),
                    end = Offset(sizePx * 5/8, sizePx * 3/4))
            }
        }
    )
}

@Preview
@Composable fun PreviewPauseI(){
    PauseIcon(MaterialTheme.colorScheme.primary)
}