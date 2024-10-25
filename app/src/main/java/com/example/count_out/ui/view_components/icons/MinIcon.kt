package com.example.count_out.ui.view_components.icons

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
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
import com.example.count_out.ui.theme.colors3

@Composable fun MinI( color: Color){
    val size = 39.dp
    val width = 1.dp
    val textMeasurer = rememberTextMeasurer()

    Canvas(Modifier.size(width = size, height = size/2 + width * 2).background(Color.Transparent)) {
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
            end = Offset(sizePx/6, sizePx/5))

    }
}

@Preview
@Composable fun PreviewMinI(){
    MinI(colors3.primary)
}