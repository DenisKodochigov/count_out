package com.example.count_out.ui.view_components.custom_view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable fun AddIcon(color: Color = MaterialTheme.colorScheme.outline, onClick: ()->Unit = {}){
    val size = 39.dp
    val width = 1.dp

    Spacer(modifier = Modifier.size(size)
        .clickable { onClick() }
        .drawWithCache {
            onDrawWithContent {
                val sizePx = size .toPx()
                val widthPx = width.toPx()
                onDrawWithContent {
                    drawOval(
                        color = color,
                        style = Stroke(width = widthPx),
                        topLeft = Offset(x = widthPx/2, y = widthPx/2),
                        size = Size(sizePx - widthPx, sizePx - widthPx))
                    drawLine(
                        color = color,
                        strokeWidth = widthPx*2,
                        start = Offset(sizePx /2, sizePx/4),
                        end = Offset(sizePx /2, sizePx * 3/4))
                    drawLine(
                        color = color,
                        strokeWidth = widthPx*2,
                        start = Offset(sizePx /4,sizePx/2),
                        end = Offset(sizePx * 3/4, sizePx /2))
                }
            }
        }
    )
}

@Composable fun MaxIcon(color: Color = MaterialTheme.colorScheme.outline, onClick: ()->Unit = {}){
    val size = 39.dp
    val width = 1.dp
    val textMeasurer = rememberTextMeasurer()

    Spacer(modifier = Modifier.size(size)
        .clickable { onClick() }
        .drawWithCache {
            onDrawWithContent {
                val sizePx = size .toPx()
                val heightText = 5.sp
                val radius = 3
                val widthPx = width.toPx()
                drawArc(
                    color = color,
                    startAngle = 0f,  sweepAngle = -180f, useCenter = true,
                    style = Stroke(width = widthPx),
                    topLeft = Offset(widthPx, widthPx),
                    size = Size(sizePx  - widthPx * 2, sizePx)
                )
                drawArc(
                    color = color,
                    startAngle = 0f,  sweepAngle = -180f, useCenter = true,
                    style = Fill,
                    topLeft = Offset(sizePx/2 - sizePx/(radius * 2), sizePx/2 - sizePx/(radius * 2) + widthPx),
                    size = Size(sizePx/radius, sizePx/radius)
                )
                val measuredMin = textMeasurer.measure(
                    AnnotatedString("min"),
                    constraints = Constraints.fixed(
                        width = (sizePx / 2f).toInt(), height = (sizePx / 2f).toInt()),
                    style = TextStyle(fontSize = heightText)
                )
                val measuredMax = textMeasurer.measure(
                    AnnotatedString("max"),
                    constraints = Constraints.fixed(
                        width = (sizePx / 2f).toInt(), height = (sizePx / 2f).toInt()),
                    style = TextStyle(fontSize = heightText)
                )
                drawText( measuredMin, color = color,
                    topLeft = Offset(widthPx*3, sizePx/2 - heightText.toPx()),)
                drawText( measuredMax, color = color,
                    topLeft = Offset(sizePx - heightText.toPx()*2 - widthPx*2, sizePx/2 - heightText.toPx()),)
                drawLine(
                    color = color,
                    strokeWidth = widthPx*2,
                    start = Offset(sizePx/2,sizePx/2),
                    end = Offset(sizePx/2 + sizePx/3, sizePx/5)
                )
            }
        }
    )
}

@Composable fun MinIcon(color: Color = MaterialTheme.colorScheme.outline, onClick: ()->Unit = {}){
    val size = 39.dp
    val width = 1.dp
    val textMeasurer = rememberTextMeasurer()

    Spacer(modifier = Modifier.size(size)
        .clickable { onClick() }
        .drawWithCache {
            onDrawWithContent {
                val sizePx = size .toPx()
                val heightText = 5.sp
                val radius = 3
                val widthPx = width.toPx()
                drawArc(
                    color = color,
                    startAngle = 0f,  sweepAngle = -180f, useCenter = true,
                    style = Stroke(width = widthPx),
                    topLeft = Offset(widthPx, widthPx),
                    size = Size(sizePx  - widthPx * 2, sizePx))
                drawArc(
                    color = color,
                    startAngle = 0f,  sweepAngle = -180f, useCenter = true,
                    style = Fill,
                    topLeft = Offset(sizePx/2 - sizePx/(radius * 2), sizePx/2 - sizePx/(radius * 2) + widthPx),
                    size = Size(sizePx/radius, sizePx/radius))
                val measuredMin = textMeasurer.measure(
                    AnnotatedString("min"),
                    constraints = Constraints.fixed(
                        width = (sizePx / 2f).toInt(), height = (sizePx / 2f).toInt()),
                    style = TextStyle(fontSize = heightText))
                val measuredMax = textMeasurer.measure(
                    AnnotatedString("max"),
                    constraints = Constraints.fixed(
                        width = (sizePx / 2f).toInt(), height = (sizePx / 2f).toInt()),
                    style = TextStyle(fontSize = heightText))
                drawText( measuredMin, color = color,
                    topLeft = Offset(widthPx*3, sizePx/2 - heightText.toPx()),)
                drawText( measuredMax, color = color,
                    topLeft = Offset(sizePx - heightText.toPx()*2 - widthPx*2, sizePx/2 - heightText.toPx()),)
                drawLine(
                    color = color,
                    strokeWidth = widthPx*2,
                    start = Offset(sizePx/2,sizePx/2),
                    end = Offset(sizePx/6, sizePx/5))
            }
        }
    )
}

@Composable fun PauseIcon(color: Color = MaterialTheme.colorScheme.outline, onClick: ()->Unit = {}){
    val size = 39.dp
    val width = 1.dp
    Spacer(modifier = Modifier.size(size)
        .clickable { onClick() }
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

@Composable fun PlayIcon(color: Color = MaterialTheme.colorScheme.outline, onClick: ()->Unit = {}){
    val size = 39.dp
    val width = 1.dp

    Spacer(modifier = Modifier.size(size)
        .clickable { onClick() }
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

@Composable fun StopIcon( color: Color = MaterialTheme.colorScheme.outline, onClick: ()->Unit = {}){
    val size = 39.dp
    val width = 1.dp
    Spacer(modifier = Modifier.size(size)
        .clickable { onClick() }
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
                drawRect(
                    color = color,
                    topLeft = Offset( sizePx/4, sizePx/4),
                    size = Size(sizePx/2, sizePx/2),
                    style = Stroke(width = widthPx)
                )
            }
        }
    )
}

@Composable fun MultiIcon( color: Color = MaterialTheme.colorScheme.outline, onClick: ()->Unit = {}){
    val size = 39.dp
    val width = 1.dp
    Spacer(modifier = Modifier.size(size)
        .clickable { onClick() }
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
                drawOval(
                    color = color,
                    topLeft = Offset(x = (sizePx/2 - widthPx * 3), y = (sizePx/5  - widthPx * 3)),
                    size = Size( widthPx * 6, widthPx * 6)
                )
                drawOval(
                    color = color,
                    topLeft = Offset(x = (sizePx/2 - widthPx * 3), y = (sizePx/2  - widthPx * 3)),
                    size = Size( widthPx * 6, widthPx * 6)
                )
                drawOval(
                    color = color,
                    topLeft = Offset(x = (sizePx/2 - widthPx * 3), y = (sizePx * 4/5  - widthPx * 3)),
                    size = Size( widthPx * 6, widthPx * 6)
                )
            }
        }
    )
}

@Composable fun CollapsingIcon( color: Color = MaterialTheme.colorScheme.outline, onClick: ()->Unit = {}){
    val size = 39.dp
    val width = 1.dp
    Spacer(modifier = Modifier.size(size)
        .clickable { onClick() }
        .drawWithCache {
            val sizePx = size .toPx()
            val widthPx = width.toPx()
            onDrawWithContent {
                drawPath(color = color, path = Path().apply {
                    moveTo( sizePx/2, sizePx * 0.4f)
                    lineTo( widthPx * 6, sizePx * 0.6f)
                    lineTo( sizePx - widthPx * 6, sizePx * 0.6f)
                    close()
                })
            }
        }
    )
}

@Composable fun UnCollapsingIcon( color: Color = MaterialTheme.colorScheme.outline, onClick: ()->Unit = {}){
    val size = 39.dp
    val width = 1.dp
    Spacer(modifier = Modifier.size(size)
        .clickable { onClick() }
        .drawWithCache {
            val sizePx = size .toPx()
            val widthPx = width.toPx()
            onDrawWithContent {
                drawPath(color = color, path = Path().apply {
                    moveTo( sizePx/2, sizePx * 0.6f)
                    lineTo( widthPx * 6, sizePx * 0.4f)
                    lineTo( sizePx - widthPx * 6, sizePx * 0.4f)
                    close()
                })
            }
        }
    )
}

@Composable fun CopyIcon( color: Color = MaterialTheme.colorScheme.outline, onClick: ()->Unit = {}){
    val size = 39.dp
    val width = 1.dp
    Spacer(modifier = Modifier.size(size)
        .clickable { onClick() }
        .drawWithCache {
            val sizePx = size .toPx()
            val widthPx = width.toPx()
            onDrawWithContent {
                val pathEffect = PathEffect.dashPathEffect(
                    intervals = floatArrayOf(3.dp.toPx(), 1.dp.toPx()), phase = 0f )
                val offset = 2.dp.toPx()
                drawRoundRect(
                    color = color,
                    topLeft = Offset(x = offset * 5, y = offset *4),
                    size = Size(sizePx/3, sizePx/2),
                    style = Stroke(width = widthPx),
                    cornerRadius = CornerRadius(x = widthPx, y = widthPx)
                )
                drawRoundRect(
                    color = color,
                    topLeft = Offset(offset * 8, offset * 6),
                    size = Size(sizePx/3, sizePx/2),
                    style = Stroke(width = widthPx, miter = 10f, cap = StrokeCap.Butt, pathEffect = pathEffect),
                    cornerRadius = CornerRadius(x = widthPx, y = widthPx)
                )
            }
        }
    )
}
@Composable fun HorLineIcon( color: Color = MaterialTheme.colorScheme.outline, onClick: ()->Unit = {}){
    val size = 39.dp
    val width = 1.dp
    Spacer(modifier = Modifier.size(size)
        .clickable { onClick() }
        .drawWithCache {
            val sizePx = size .toPx()
            val widthPx = width.toPx()
            onDrawWithContent {
                drawLine(
                    color = color,
                    strokeWidth = widthPx*2,
                    start = Offset(sizePx /4,sizePx/2),
                    end = Offset(sizePx * 3/4, sizePx /2))
            }
        }
    )
}
@Composable fun MarkIcon( color: Color = MaterialTheme.colorScheme.outline, onClick: ()->Unit = {}){
    val size = 39.dp
    val width = 1.dp
    Spacer(modifier = Modifier.size(size)
        .clickable { onClick() }
        .drawWithCache {
            val sizePx = size .toPx()
            val widthPx = width.toPx()
            onDrawWithContent {
                drawLine(
                    color = color,
                    strokeWidth = widthPx*2,
                    start = Offset(sizePx /4, sizePx/3),
                    end = Offset(sizePx /2, sizePx * 3/4))
                drawLine(
                    color = color,
                    strokeWidth = widthPx*2,
                    start = Offset(sizePx/2 - widthPx + 1,sizePx * 3/4),
                    end = Offset(sizePx * 3/4, sizePx/5))
            }
        }
    )
}
@Preview
@Composable fun Preview() { AddIcon(MaterialTheme.colorScheme.outline)}
@Preview
@Composable fun Preview1() { HorLineIcon(color = MaterialTheme.colorScheme.outline)}