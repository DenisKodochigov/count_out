package com.count_out.presentation.view_element

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Border(modifier: Modifier = Modifier, open: Boolean, content: @Composable () -> Unit) {
    val strokeWidth: Dp = 1.dp
    val cornerRadius: Dp = 2.dp
    val color =  Color.LightGray//colorScheme.surfaceContainerLow
    Box( modifier = modifier
        .borderMy(cornerRadius, strokeWidth, open, color = color)
        .clip(RoundedCornerShape(topStart = cornerRadius, bottomStart = cornerRadius)),
        content = { content() }
    )
}

@Composable fun Modifier.borderMy(radius: Dp, strokeWidth: Dp, open: Boolean, color: Color) = Modifier.padding(2.dp)
    .drawBehind {
        val strokeWidthPx = strokeWidth.toPx()
        val rPx = radius.toPx()
        val dPx = rPx * 2
        val x = size.width
        val y = size.height
        val rectSB = Rect(0f, y - dPx, dPx, y)
        val rectEB = Rect(x, y , x + dPx, y + dPx)
        val rectST = Rect(0f, 0f, dPx, dPx)
        val rectET = Rect(x, 0f - dPx, x + dPx, 0f)

        val rectEB1 = Rect(x - dPx, y - dPx , x, y)
        val rectET1 = Rect(x - dPx, 0f, x, 0f + dPx)

        val pathOpen = Path().apply {
            moveTo(x + dPx, y + rPx)
            arcTo( rectEB, startAngleDegrees = 0f, sweepAngleDegrees = -90f, forceMoveTo = false )
            lineTo(x , y)
            arcTo( rectSB, startAngleDegrees = 90f, sweepAngleDegrees = 90f, forceMoveTo = false)
            lineTo(0f, rPx)
            arcTo( rectST, startAngleDegrees = 180f, sweepAngleDegrees = 90f, forceMoveTo = false)
            lineTo(x, 0f)
            arcTo( rectET, startAngleDegrees = 90f, sweepAngleDegrees = -90f, forceMoveTo = false)
        }
        val pathClose = Path().apply {
            moveTo(x, y - dPx)
            arcTo( rectEB1, startAngleDegrees = 0f, sweepAngleDegrees = 90f, forceMoveTo = false )
            lineTo(x - dPx, y)
            arcTo( rectSB, startAngleDegrees = 90f, sweepAngleDegrees = 90f, forceMoveTo = false)
            lineTo(0f, rPx)
            arcTo( rectST, startAngleDegrees = 180f, sweepAngleDegrees = 90f, forceMoveTo = false)
            lineTo(x- dPx, 0f)
            arcTo( rectET1, startAngleDegrees = 270f, sweepAngleDegrees = 90f, forceMoveTo = false)
            lineTo(x, y - dPx)
            moveTo(x + dPx, 0f - rPx * 1.4f)
            lineTo(x + dPx, y + rPx * 1.4f)
        }
        drawPath(if (open) pathOpen else pathClose, color, style = Stroke(width = strokeWidthPx))
    }
@Preview(showBackground = true)
@Composable fun previewModel( ) {
//    TemplateList1()
}