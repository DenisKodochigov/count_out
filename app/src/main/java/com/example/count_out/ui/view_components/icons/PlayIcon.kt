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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable fun PlayIcon(color: Color){
    val size = 39.dp
    val width = 1.dp

    Spacer(modifier = Modifier.size(size)
        .drawWithCache {
            val sizePx = size .toPx()
            val widthPx = width.toPx()
            val offsetX = 2.dp.toPx()
            val triangle = Path().apply {
                moveTo(sizePx/3 + offsetX, sizePx/3)
                lineTo(sizePx * 2/3 + offsetX,sizePx / 2)
                lineTo(sizePx /3 + offsetX,sizePx * 2 / 3)
                lineTo(sizePx/3 + offsetX, sizePx/3)
                close()
            }
            onDrawWithContent {
                drawOval(
                    color = color,
                    style = Stroke(width = widthPx),
                    topLeft = Offset(x = widthPx/2, y = widthPx/2),
                    size = Size(sizePx - widthPx, sizePx - widthPx)
                )
                drawPath( color = color, path = triangle, style = Stroke(width = widthPx), )
            }
        }
    )
}

@Preview
@Composable fun PreviewPlayIcon(){
    PlayIcon(MaterialTheme.colorScheme.primary)
}