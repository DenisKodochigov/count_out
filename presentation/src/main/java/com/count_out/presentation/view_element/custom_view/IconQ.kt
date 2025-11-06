package com.count_out.presentation.view_element.custom_view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

@Preview
@Composable fun Preview() {
    IconQ.ArrowChordCanvas()
}

object IconQ{
    private val fontSize = 9.sp
    private val width = 35.dp
    private val height = 35.dp
    private val thick = 1.dp
    private const val STROKE_WIDTH = 3f
    private const val ARROW_WIDTH_HOR = 2f
    private const val DELTA_UP_DOWN_Y = 4f
    private const val DELTA_UP_DOWN_X = 1f
    private const val DELTA_LEFT_RIGHT_Y = 1f
    private const val DELTA_LEFT_RIGHT_X = 4f
    private const val HEIGHT_HOR = 20f
    private const val WIDTH_HOR = 10f
    private const val HEIGHT_VER = 10f
    private const val WIDTH_VER = 40f

    fun text(){}
    @Composable fun color() = MaterialTheme.colorScheme.outline
    @Composable fun ArrowLeft() {
        val colorLine = MaterialTheme.colorScheme.primary
        Spacer(modifier = Modifier
            .height(Dp(HEIGHT_HOR))
            .width(Dp(WIDTH_HOR))
            .drawWithCache {
                onDrawWithContent {
                    drawLine(
                        color = colorLine,
                        strokeWidth = ARROW_WIDTH_HOR,
                        start = Offset(x = DELTA_LEFT_RIGHT_X, y = size.height / 2 + DELTA_LEFT_RIGHT_Y),
                        end = Offset(x = size.width - DELTA_LEFT_RIGHT_X, y = 0f + DELTA_LEFT_RIGHT_Y),
                    )
                    drawLine(
                        color = colorLine,
                        strokeWidth = ARROW_WIDTH_HOR,
                        start = Offset(x = DELTA_LEFT_RIGHT_X, y = size.height / 2 - DELTA_LEFT_RIGHT_Y),
                        end = Offset(
                            x = size.width - DELTA_LEFT_RIGHT_X,
                            y = size.height - DELTA_LEFT_RIGHT_Y
                        ),
                    )
                }
            }
        )
    }
    @Composable fun ArrowRight() {
        val colorLine = MaterialTheme.colorScheme.primary
        Spacer(modifier = Modifier
            .height(Dp(HEIGHT_HOR))
            .width(Dp(WIDTH_HOR))
            .drawWithCache {
                onDrawWithContent {
                    drawLine(
                        color = colorLine,
                        strokeWidth = ARROW_WIDTH_HOR,
                        start = Offset(
                            x = size.width - DELTA_LEFT_RIGHT_X,
                            y = size.height / 2 + DELTA_LEFT_RIGHT_Y
                        ),
                        end = Offset(x = DELTA_LEFT_RIGHT_X, y = 0f + DELTA_LEFT_RIGHT_Y),
                    )
                    drawLine(
                        color = colorLine,
                        strokeWidth = ARROW_WIDTH_HOR,
                        start = Offset(
                            x = size.width - DELTA_LEFT_RIGHT_X,
                            y = size.height / 2 - DELTA_LEFT_RIGHT_Y
                        ),
                        end = Offset(x = DELTA_LEFT_RIGHT_X, y = size.height - DELTA_LEFT_RIGHT_Y),
                    )
                }
            }
        )
    }
    @Composable fun ArrowUp() {
        val colorLine = MaterialTheme.colorScheme.primary

        Spacer(modifier = Modifier
            .height(Dp(HEIGHT_HOR))
            .width(Dp(WIDTH_VER))
            .drawWithCache {
                onDrawWithContent {
                    drawLine(
                        color = colorLine,
                        strokeWidth = STROKE_WIDTH,
                        start = Offset(x = 0f + DELTA_UP_DOWN_X, y = size.height - DELTA_UP_DOWN_Y),
                        end = Offset(x = size.width / 2 + DELTA_UP_DOWN_X, y = 0f + DELTA_UP_DOWN_Y),
                    )

                    drawLine(
                        color = colorLine,
                        strokeWidth = STROKE_WIDTH,
                        start = Offset(x = size.width / 2 - DELTA_UP_DOWN_X, y = 0f + DELTA_UP_DOWN_Y),
                        end = Offset(x = size.width - DELTA_UP_DOWN_X, y = size.height - DELTA_UP_DOWN_Y),
                    )
                }
            }
        )
    }
    @Composable fun ArrowDown() {
        val colorLine = MaterialTheme.colorScheme.primary

        Spacer(modifier = Modifier
            .height(Dp(HEIGHT_HOR))
            .width(Dp(WIDTH_VER))
            .drawWithCache {
                onDrawWithContent {
                    drawLine(
                        color = colorLine,
                        strokeWidth = STROKE_WIDTH,
                        start = Offset(x = 0f + DELTA_UP_DOWN_X, y = 0f + DELTA_UP_DOWN_Y),
                        end = Offset(
                            x = size.width / 2 + DELTA_UP_DOWN_X,
                            y = size.height - DELTA_UP_DOWN_Y
                        ),
                    )
                    drawLine(
                        color = colorLine,
                        strokeWidth = STROKE_WIDTH,
                        start = Offset(
                            x = size.width / 2 - DELTA_UP_DOWN_X,
                            y = size.height - DELTA_UP_DOWN_Y
                        ),
                        end = Offset(x = size.width - DELTA_UP_DOWN_X, y = 0f + DELTA_UP_DOWN_Y),
                    )
                }
            }
        )
    }
    @Composable fun ArrowNoneVer(){
        Canvas(modifier = Modifier
            .height(Dp(HEIGHT_VER))
            .width(Dp(WIDTH_VER))) {}
    }
    @Composable fun ArrowNoneHor(){
        Canvas(modifier = Modifier
            .height(Dp(HEIGHT_HOR))
            .width(Dp(WIDTH_HOR))) {}
    }
    @Composable fun Add(color: Color = color(), onClick: ()->Unit = {}){
        Spacer(modifier = Modifier
            .width(width)
            .height(height)
            .clickable { onClick() }
            .drawWithCache {
                onDrawWithContent {
                    val xPx = width.toPx()
                    val yPx = height.toPx()
                    val thickPx = thick.toPx()
                    drawOval(
                        color = color,
                        style = Stroke(width = thickPx),
                        topLeft = Offset(x = thickPx / 2, y = thickPx / 2),
                        size = Size(xPx - thickPx, yPx - thickPx)
                    )
                    drawLine(
                        color = color,
                        strokeWidth = thickPx,
                        start = Offset(xPx / 2, yPx / 4),
                        end = Offset(xPx / 2, yPx * 3 / 4)
                    )
                    drawLine(
                        color = color,
                        strokeWidth = thickPx,
                        start = Offset(xPx / 4, yPx / 2),
                        end = Offset(xPx * 3 / 4, yPx / 2)
                    )
                }
            }
        )
    }
    @Composable fun Faster(modifier: Modifier = Modifier, color: Color = color(), onClick: ()->Unit = {}){
        val textMeasurer = rememberTextMeasurer()
        Spacer(modifier = modifier
            .width(width)
            .height(height /2 + thick *2)
            .clickable { onClick() }
            .drawWithCache {
                onDrawWithContent {
                    val xPx = width.toPx()
                    val yPx = height.toPx()
                    val heightText = 5.sp
                    val radius = 3
                    val thickPx = thick.toPx()
                    drawRoundRect(
                        color = color,
                        topLeft = Offset(x = thickPx / 2, y = thickPx / 2),
                        size = Size(xPx - thickPx, yPx - thickPx),
                        cornerRadius = CornerRadius(8f, 8f),
                        style = Stroke(width = thickPx),
                    )
                    drawArc(
                        color = color,
                        startAngle = 0f, sweepAngle = -180f, useCenter = true,
                        style = Stroke(width = thickPx),
                        topLeft = Offset(thickPx, thickPx),
                        size = Size(xPx - thickPx * 2, yPx)
                    )
                    drawArc(
                        color = color,
                        startAngle = 0f, sweepAngle = -180f, useCenter = true,
                        style = Fill,
                        topLeft = Offset(
                            xPx / 2 - xPx / (radius * 2),
                            yPx / 2 - yPx / (radius * 2) + thickPx
                        ),
                        size = Size(xPx / radius, yPx / radius)
                    )
                    val measuredMin = textMeasurer.measure(
                        AnnotatedString("min"),
                        constraints = Constraints.fixed(
                            width = (xPx / 2f).toInt(), height = (yPx / 2f).toInt()
                        ),
                        style = TextStyle(fontSize = heightText)
                    )
                    val measuredMax = textMeasurer.measure(
                        AnnotatedString("max"),
                        constraints = Constraints.fixed(
                            width = (xPx / 2f).toInt(), height = (yPx / 2f).toInt()
                        ),
                        style = TextStyle(fontSize = heightText)
                    )
                    drawText(
                        measuredMin, color = color,
                        topLeft = Offset(thickPx * 3, yPx / 2 - heightText.toPx()),
                    )
                    drawText(
                        measuredMax, color = color,
                        topLeft = Offset(
                            xPx - heightText.toPx() * 2 - thickPx * 2,
                            yPx / 2 - heightText.toPx()
                        ),
                    )
                    drawLine(
                        color = color,
                        strokeWidth = thickPx * 2,
                        start = Offset(xPx / 2, yPx / 2),
                        end = Offset(xPx / 2 + xPx / 3, yPx / 5)
                    )
                }
            }
        )
    }
    @Composable fun Slower(modifier: Modifier = Modifier, color: Color = color(), onClick: ()->Unit = {}){
        val textMeasurer = rememberTextMeasurer()
        Spacer(modifier = modifier
            .width(width)
            .height(height /2 + thick *2)
            .clickable { onClick() }
            .drawWithCache {
                onDrawWithContent {
                    val xPx = width.toPx()
                    val yPx = height.toPx()
                    val heightText = 5.sp
                    val radius = 3
                    val thickPx = thick.toPx()
                    drawRoundRect(
                        color = color,
                        topLeft = Offset(x = thickPx / 2, y = thickPx / 2),
                        size = Size(xPx - thickPx, yPx - thickPx),
                        cornerRadius = CornerRadius(8f, 8f),
                        style = Stroke(width = thickPx),
                    )
                    drawArc(
                        color = color,
                        startAngle = 0f, sweepAngle = -180f, useCenter = true,
                        style = Stroke(width = thickPx),
                        topLeft = Offset(thickPx, thickPx),
                        size = Size(xPx - thickPx * 2, yPx)
                    )
                    drawArc(
                        color = color,
                        startAngle = 0f, sweepAngle = -180f, useCenter = true,
                        style = Fill,
                        topLeft = Offset(
                            xPx / 2 - xPx / (radius * 2),
                            yPx / 2 - yPx / (radius * 2) + thickPx
                        ),
                        size = Size(xPx / radius, yPx / radius)
                    )
                    val measuredMin = textMeasurer.measure(
                        AnnotatedString("min"),
                        constraints = Constraints.fixed(
                            width = (xPx / 2f).toInt(), height = (yPx / 2f).toInt()
                        ),
                        style = TextStyle(fontSize = heightText)
                    )
                    val measuredMax = textMeasurer.measure(
                        AnnotatedString("max"),
                        constraints = Constraints.fixed(
                            width = (xPx / 2f).toInt(), height = (yPx / 2f).toInt()
                        ),
                        style = TextStyle(fontSize = heightText)
                    )
                    drawText(
                        measuredMin, color = color,
                        topLeft = Offset(thickPx * 3, yPx / 2 - heightText.toPx()),
                    )
                    drawText(
                        measuredMax, color = color,
                        topLeft = Offset(
                            xPx - heightText.toPx() * 2 - thickPx * 2,
                            yPx / 2 - heightText.toPx()
                        ),
                    )
                    drawLine(
                        color = color,
                        strokeWidth = thickPx * 2,
                        start = Offset(xPx / 2, yPx / 2),
                        end = Offset(xPx / 6, yPx / 5)
                    )
                }
            }
        )
    }
    @Composable fun Pause(color: Color = color(), onClick: ()->Unit = {}){
        Spacer(modifier = Modifier
            .width(width)
            .height(height)
            .clickable { onClick() }
            .drawWithCache {
                onDrawWithContent {
                    val xPx = width.toPx()
                    val yPx = height.toPx()
                    val thickPx = thick.toPx()
                    drawRoundRect(
                        color = color,
                        topLeft = Offset(x = thickPx / 2, y = thickPx / 2),
                        size = Size(xPx - thickPx, yPx - thickPx),
                        cornerRadius = CornerRadius(8f, 8f),
                        style = Stroke(width = thickPx),
                    )
//                    drawOval(
//                        color = color,
//                        style = Stroke(width = thickPx),
//                        topLeft = Offset(x = thickPx / 2, y = thickPx / 2),
//                        size = Size(xPx - thickPx, yPx - thickPx)
//                    )
                    drawLine(
                        color = color,
                        strokeWidth = thickPx * 2,
                        start = Offset(xPx * 3 / 8, yPx / 4),
                        end = Offset(xPx * 3 / 8, yPx * 3 / 4)
                    )
                    drawLine(
                        color = color,
                        strokeWidth = thickPx * 2,
                        start = Offset(xPx * 5 / 8, yPx / 4),
                        end = Offset(xPx * 5 / 8, yPx * 3 / 4)
                    )
                }
            }
        )
    }
    @Composable fun Play(color: Color = color(), onClick: ()->Unit = {}){
        Spacer(modifier = Modifier
            .width(width)
            .height(height)
            .clickable { onClick() }
            .drawWithCache {
                onDrawWithContent {
                    val xPx = width.toPx()
                    val yPx = height.toPx()
                    val thickPx = thick.toPx()
                    val offsetX = 2.dp.toPx()
                    val triangle = Path().apply {
                        moveTo(xPx * 0.3f + offsetX, yPx / 4)
                        lineTo(xPx * 0.7f + offsetX, yPx / 2)
                        lineTo(xPx * 0.3f + offsetX, yPx * 3 / 4)
                        lineTo(xPx * 0.3f + offsetX, yPx / 3)
                        close()
                    }
                    drawRoundRect(
                        color = color,
                        topLeft = Offset(x = thickPx / 2, y = thickPx / 2),
                        size = Size(xPx - thickPx, yPx - thickPx),
                        cornerRadius = CornerRadius(8f, 8f),
                        style = Stroke(width = thickPx),
                    )
//                    drawOval(
//                        color = color,
//                        style = Stroke(width = thickPx),
//                        topLeft = Offset(x = thickPx / 2, y = thickPx / 2),
//                        size = Size(xPx - thickPx, yPx - thickPx)
//                    )
                    drawPath(color = color, path = triangle, style = Stroke(width = thickPx))
                }
            }
        )
    }
    @Composable fun Stop(color: Color = color(), onClick: ()->Unit = {}){
        Spacer(modifier = Modifier
            .width(width)
            .height(height)
            .clickable { onClick() }
            .drawWithCache {
                onDrawWithContent {
                    val xPx = width.toPx()
                    val yPx = height.toPx()
                    val thickPx = thick.toPx()
                    drawRoundRect(
                        color = color,
                        topLeft = Offset(x = thickPx / 2, y = thickPx / 2),
                        size = Size(xPx - thickPx, yPx - thickPx),
                        cornerRadius = CornerRadius(8f, 8f),
                        style = Stroke(width = thickPx),
                    )
//                    drawOval(
//                        color = color,
//                        style = Stroke(width = thickPx),
//                        topLeft = Offset(x = thickPx / 2, y = thickPx / 2),
//                        size = Size(xPx - thickPx, yPx - thickPx)
//                    )
                    drawRect(
                        color = color,
                        topLeft = Offset(xPx / 4, yPx / 4),
                        size = Size(xPx / 2, yPx / 2),
                        style = Stroke(width = thickPx)
                    )
                }
            }
        )
    }
    @Composable fun Multi(color: Color = color(), onClick: ()->Unit = {}){
        Spacer(modifier = Modifier
            .width(width)
            .height(height)
            .clickable { onClick() }
            .drawWithCache {
                onDrawWithContent {
                    val xPx = width.toPx()
                    val yPx = height.toPx()
                    val thickPx = thick.toPx()
                    val diameter = 5f
                    val x0 = xPx / 2 - thickPx * diameter / 2
                    val y0 = yPx / 2 - thickPx * diameter / 2
                    val delta1 = yPx * 0.2f
//                    drawRoundRect(
//                        color = color,
//                        topLeft = Offset(x = thickPx / 2, y = thickPx / 2),
//                        size = Size(xPx - thickPx, yPx - thickPx),
//                        cornerRadius = CornerRadius(8f, 8f),
//                        style = Stroke(width = thickPx),
//                    )
                    drawOval(
                        color = color, topLeft = Offset(x = x0, y = y0 - delta1),
                        size = Size(thickPx * diameter, thickPx * diameter)
                    )
                    drawOval(
                        color = color, topLeft = Offset(x = x0, y = y0),
                        size = Size(thickPx * diameter, thickPx * diameter)
                    )
                    drawOval(
                        color = color, topLeft = Offset(x = x0, y = y0 + delta1),
                        size = Size(thickPx * diameter, thickPx * diameter)
                    )
                }
            }
        )
    }
    @Composable fun Multi1(color: Color = color(), onClick: ()->Unit = {}){
        val width = 30.dp
        val height = 30.dp
        Spacer(modifier = Modifier
            .width(width)
            .height(height)
            .clickable { onClick() }
            .drawWithCache {
                onDrawWithContent {
                    val xPx = width.toPx()
                    val yPx = height.toPx()
                    val thickPx = thick.toPx()
                    val diameter = 5f
                    val x0 = xPx / 2 - thickPx * diameter / 2
                    val y0 = yPx / 2 - thickPx * diameter / 2
                    val delta1 = yPx * 0.2f
                    drawRoundRect(
                        color = color,
                        topLeft = Offset(x = thickPx / 2, y = thickPx / 2),
                        size = Size(xPx - thickPx, yPx - thickPx),
                        cornerRadius = CornerRadius(8f, 8f),
                        style = Stroke(width = thickPx),
                    )
                    drawOval(
                        color = color,
                        topLeft = Offset(x = x0 - delta1, y = y0),
                        size = Size(thickPx * diameter, thickPx * diameter)
                    )
                    drawOval(
                        color = color,
                        topLeft = Offset(x = x0, y = y0),
                        size = Size(thickPx * diameter, thickPx * diameter)
                    )
                    drawOval(
                        color = color,
                        topLeft = Offset(x = x0 + delta1, y = y0),
                        size = Size(thickPx * diameter, thickPx * diameter)
                    )
                }
            }
        )
    }

    @Composable fun Collapsing(color: Color = color(), onClick: ()->Unit = {}){
        Spacer(modifier = Modifier
            .width(width)
            .height(height)
            .clickable { onClick() }
            .drawWithCache {
                onDrawWithContent {
                    val xPx = width.toPx()
                    val yPx = height.toPx()
                    val thickPx = thick.toPx()
//                    drawRoundRect(
//                        color = color,
//                        topLeft = Offset(x = thickPx / 2, y = thickPx / 2),
//                        size = Size(xPx - thickPx, yPx - thickPx),
//                        cornerRadius = CornerRadius(8f, 8f),
//                        style = Stroke(width = thickPx),
//                    )
                    drawPath(color = color, path = Path().apply {
                        moveTo(xPx / 2, yPx * 0.4f)
                        lineTo(thickPx * 9, yPx * 0.6f)
                        lineTo(xPx - thickPx * 9, yPx * 0.6f)
                        close()
                    })
                }
            }
        )
    }
    @Composable fun UnCollapsing(color: Color = color(), onClick: ()->Unit = {}){
        Spacer(modifier = Modifier
            .width(width)
            .height(height)
            .clickable { onClick() }
            .drawWithCache {
                onDrawWithContent {
                    val xPx = width.toPx()
                    val yPx = height.toPx()
                    val thickPx = thick.toPx()
//                    drawRoundRect(
//                        color = color,
//                        topLeft = Offset(x = thickPx / 2, y = thickPx / 2),
//                        size = Size(xPx - thickPx, yPx - thickPx),
//                        cornerRadius = CornerRadius(8f, 8f),
//                        style = Stroke(width = thickPx),
//                    )
                    drawPath(color = color, path = Path().apply {
                        moveTo(xPx / 2, yPx * 0.6f)
                        lineTo(thickPx * 9, yPx * 0.4f)
                        lineTo(xPx - thickPx * 9, yPx * 0.4f)
                        close()
                    })
                }
            }
        )
    }
    @Composable fun Copy(color: Color = color(), onClick: ()->Unit = {}){
        Spacer(modifier = Modifier
            .width(width)
            .height(height)
            .clickable { onClick() }
            .drawWithCache {
                onDrawWithContent {
                    val xPx = width.toPx()
                    val yPx = height.toPx()
                    val thickPx = thick.toPx()
                    val pathEffect = PathEffect.dashPathEffect(
                        intervals = floatArrayOf(3.dp.toPx(), 1.dp.toPx()), phase = 0f
                    )
                    val offset = 2.dp.toPx()
                    drawRoundRect(
                        color = color,
                        topLeft = Offset(x = offset * 5, y = offset * 4),
                        size = Size(xPx / 3, yPx / 2),
                        style = Stroke(width = thickPx),
                        cornerRadius = CornerRadius(x = thickPx, y = thickPx)
                    )
                    drawRoundRect(
                        color = color,
                        topLeft = Offset(offset * 8, offset * 6),
                        size = Size(xPx / 3, yPx / 2),
                        style = Stroke(
                            width = thickPx,
                            miter = 10f,
                            cap = StrokeCap.Butt,
                            pathEffect = pathEffect
                        ),
                        cornerRadius = CornerRadius(x = thickPx, y = thickPx)
                    )
                }
            }
        )
    }
    @Composable fun HorLine(color: Color = color(), onClick: ()->Unit = {}){
        Spacer(modifier = Modifier
            .width(width)
            .height(height)
            .clickable { onClick() }
            .drawWithCache {
                onDrawWithContent {
                    val xPx = width.toPx()
                    val yPx = height.toPx()
                    val thickPx = thick.toPx()
                    drawLine(
                        color = color,
                        strokeWidth = thickPx,
                        start = Offset(xPx / 4, yPx / 2),
                        end = Offset(xPx * 3 / 4, yPx / 2)
                    )
                }
            }
        )
    }
    @Composable fun Mark(color: Color = color(), onClick: ()->Unit = {}){
        Spacer(modifier = Modifier
            .width(width)
            .height(height)
            .clickable { onClick() }
            .drawWithCache {
                onDrawWithContent {
                    val xPx = width.toPx()
                    val yPx = height.toPx()
                    val thickPx = thick.toPx()
                    drawLine(
                        color = color,
                        strokeWidth = thickPx * 2,
                        start = Offset(xPx / 4, yPx / 3),
                        end = Offset(xPx / 2, yPx * 3 / 4)
                    )
                    drawLine(
                        color = color,
                        strokeWidth = thickPx * 2,
                        start = Offset(xPx / 2 - thickPx + 1, yPx * 3 / 4),
                        end = Offset(xPx * 3 / 4, yPx / 5)
                    )
                }
            }
        )
    }
    @Composable fun RingExercise(selected: Boolean = false, color: Color = color(), onClick: ()->Unit = {}){
        val colorO = colorSelected(selected, color)
        val colorL = colorSelected(!selected, color)
        Spacer(modifier = Modifier
            .width(width)
            .height(height)
            .clickable { onClick() }
            .drawWithCache {
                onDrawWithContent {
                    val yPx1 = (height * 0.1f).toPx()
                    val thickPx = 1.dp.toPx() * if (selected) 3 else 2
                    val startAngle = -83f
                    val sweepAngle = 346f
                    val sizeOval = width.toPx()*0.7f
                    val delta = sizeOval * 0.44f
                    val xPx = width.toPx()*0.7f + thickPx - delta
                    drawArc(
                        color = colorO,
                        startAngle = startAngle,
                        sweepAngle = sweepAngle,
                        useCenter = false,
                        topLeft = Offset(x = thickPx / 2, y = yPx1),
                        size = Size(sizeOval, sizeOval),
                        style = Stroke(width = thickPx)
                    )
                    drawLine(
                        color = colorO, strokeWidth = thickPx * 1,
                        start = Offset(delta - 2f, 0f),
                        end = Offset(delta, yPx1)
                    )
                    drawLine(
                        color = colorO, strokeWidth = thickPx * 1,
                        start = Offset(xPx + 2f, 0f),
                        end = Offset(xPx, yPx1)
                    )
                    val xSt = width.toPx()*1/3
                    val xEn = width.toPx()
                    val yPx = height.toPx()
                    val delta2 = yPx * 0.2f
                    val thickPx2 = 1.dp.toPx()
                    val strokeWidth = thickPx2 * if (!selected) 3 else 2
                    var koef = 2
                    drawLine(
                        color = colorL, strokeWidth = strokeWidth,
                        start = Offset(xSt, thickPx2 * 2 + delta2 * koef),
                        end = Offset(xEn, thickPx2 * 2 + delta2 * koef++)
                    )
                    drawLine(
                        color = colorL, strokeWidth = strokeWidth,
                        start = Offset(xSt, thickPx2 * 2 + delta2 * koef),
                        end = Offset(xEn, thickPx2 * 2 + delta2 * koef++)
                    )
                    drawLine(
                        color = colorL, strokeWidth = strokeWidth,
                        start = Offset(xSt, thickPx2 * 2 + delta2 * koef),
                        end = Offset(xEn, thickPx2 * 2 + delta2 * koef++)
                    )
//                    drawLine(
//                        color = colorL, strokeWidth = strokeWidth,
//                        start = Offset(xSt, thickPx2 * 2 + delta2 * koef),
//                        end = Offset(xEn, thickPx2 * 2 + delta2 * koef++)
//                    )
//                    drawLine(
//                        color = colorL, strokeWidth = strokeWidth,
//                        start = Offset(xSt, thickPx2 * 2 + delta2 * koef),
//                        end = Offset(xEn, thickPx2 * 2 + delta2 * koef)
//                    )
                }
            }
        )
    }
    @Composable fun Distance(selected: Boolean = false, color: Color = color(), onClick: ()->Unit = {}){
        val colorL = colorSelected(selected, color)
        val style = styleSelected (selected, color, fontSize)
        val textMeasurer = rememberTextMeasurer()
        Spacer(modifier = Modifier
            .width(width)
            .height(height)
            .clickable { onClick() }
            .drawWithCache {
                onDrawWithContent {
                    val xPx = width.toPx()
                    val yPx = (height * 0.7f).toPx()
                    val yPx0 = (height).toPx()
                    val delta1 = xPx * 0.18f
                    val thickPx = 1.dp.toPx()
                    drawRoundRect(
                        color = color,
                        topLeft = Offset(x = thickPx / 2, y = thickPx / 2),
                        size = Size(xPx - thickPx, yPx - thickPx),
                        cornerRadius = CornerRadius(8f, 8f),
                        style = Stroke(width = thickPx),
                    )
//                    drawOval(
//                        color = colorL, style = Stroke(width = thickPx),
//                        topLeft = Offset(x = thickPx / 2, y = thickPx / 2),
//                        size = Size(xPx - thickPx, yPx0 - thickPx)
//                    )
                    drawLine(
                        color = colorL, strokeWidth = thickPx,
                        start = Offset(delta1, yPx), end = Offset(xPx - delta1, yPx)
                    )
                    drawLine(
                        color = colorL, strokeWidth = thickPx * 2,
                        start = Offset(delta1, yPx - thickPx * 4),
                        end = Offset(delta1, yPx)
                    )
                    drawLine(
                        color = colorL, strokeWidth = thickPx,
                        start = Offset(xPx / 2, yPx - thickPx * 3),
                        end = Offset(xPx / 2, yPx)
                    )
                    drawLine(
                        color = colorL, strokeWidth = thickPx * 2,
                        start = Offset(xPx - delta1, yPx - thickPx * 4),
                        end = Offset(xPx - delta1, yPx)
                    )
                    val textLayout: TextLayoutResult =
                        textMeasurer.measure(text = AnnotatedString("km"), style = style)
                    drawText(
                        textLayout, topLeft = Offset(
                            (xPx - textLayout.size.width)/2,
                            yPx - textLayout.size.height * 1.3f
                        )
                    )
                }
            }
        )
    }
    @Composable fun Count(selected: Boolean = false, color: Color = color(), onClick: ()->Unit = {}){
        val style = styleSelected (selected, color, fontSize)
        val colorL = colorSelected(selected, color)
        val textMeasurer = rememberTextMeasurer()
        Spacer(modifier = Modifier
            .width(width)
            .height(height)
            .clickable { onClick() }
            .drawWithCache {
                onDrawWithContent {
                    val thickPx = 1.dp.toPx()
                    val xPx = width.toPx()
                    val yPx = height.toPx()
                    drawRoundRect(
                        color = colorL,
                        topLeft = Offset(x = thickPx / 2, y = thickPx / 2),
                        size = Size(xPx - thickPx, yPx - thickPx),
                        cornerRadius = CornerRadius(8f, 8f),
                        style = Stroke(width = thickPx),
                    )
//                    drawOval(
//                        color = colorL, style = Stroke(width = thickPx),
//                        topLeft = Offset(x = thickPx / 2, y = thickPx / 2),
//                        size = Size(xPx - thickPx, yPx - thickPx)
//                    )
                    val textLayout: TextLayoutResult =
                        textMeasurer.measure(text = AnnotatedString("1.2.3"), style = style)
                    drawText(
                        textLayout, topLeft = Offset(
                            (xPx - textLayout.size.width) / 2f,
                            (yPx - textLayout.size.height) * 0.5f
                        )
                    )
                }
            }
        )
    }
    @Composable fun Duration(selected: Boolean = false, onClick: ()->Unit = {}, color: Color = color(),) {
        val colorL = colorSelected(selected, color)

        Spacer(modifier = Modifier
            .width(width)
            .height(height)
            .clickable { onClick() }
            .drawWithCache {
                onDrawWithContent {
                    val xPx = width.toPx()
                    val yPx = height.toPx() * 0.67f
                    val yPx0 = height.toPx()
                    val thickPx = 1.dp.toPx()
                    val widthPx2 = if (selected) 2.dp.toPx() else 1.dp.toPx()

                    val diameter = yPx * 0.7f
                    val diameter1 = diameter * 0.15f
                    val x0 = xPx / 2 - diameter / 2
                    val y0 = yPx - diameter * 0.7f
                    val metka = diameter / 8
                    val arrow = diameter / 4
                    val x1 = diameter / 8
                    val y1 = diameter / 5
                    val x2 = diameter / 4
                    val y2 = diameter / 3
                    val y21 = diameter / 10
                    drawRoundRect(
                        color = colorL,
                        topLeft = Offset(x = thickPx / 2, y = thickPx / 2),
                        size = Size(xPx - thickPx, yPx - thickPx),
                        cornerRadius = CornerRadius(8f, 8f),
                        style = Stroke(width = thickPx),
                    )
//                    drawOval(
//                        color = colorL, style = Stroke(width = thickPx),
//                        topLeft = Offset(x = thickPx / 2, y = thickPx / 2),
//                        size = Size(xPx - thickPx, yPx0 - thickPx)
//                    )
                    drawOval(
                        color = colorL, style = Stroke(width = widthPx2),
                        topLeft = Offset(x = x0, y = y0),
                        size = Size(diameter, diameter)
                    )
                    drawLine(
                        color = colorL, strokeWidth = thickPx,
                        start = Offset(x0 + diameter / 2, y0 + diameter - metka),
                        end = Offset(x0 + diameter / 2, y0 + diameter)
                    )
                    drawLine(
                        color = colorL, strokeWidth = thickPx,
                        start = Offset(x0 + diameter / 2, y0),
                        end = Offset(x0 + diameter / 2, y0 + metka)
                    )
                    drawLine(
                        color = colorL, strokeWidth = thickPx,
                        start = Offset(x0, y0 + diameter / 2),
                        end = Offset(x0 + metka, y0 + diameter / 2)
                    )
                    drawLine(
                        color = colorL, strokeWidth = thickPx,
                        start = Offset(x0 + diameter, y0 + diameter / 2),
                        end = Offset(x0 + diameter - metka, y0 + diameter / 2)
                    )
                    drawLine(
                        color = colorL, strokeWidth = thickPx,
                        start = Offset(x0 + diameter / 2, y0 + diameter / 2),
                        end = Offset(x0 + diameter / 2 + arrow, y0 + diameter / 2 - arrow)
                    )
                    drawOval(
                        color = colorL,
                        topLeft = Offset(
                            x0 + (diameter - diameter1) / 2,
                            y0 + (diameter - diameter1) / 2
                        ),
                        size = Size(diameter1, diameter1)
                    )
                    drawLine(
                        color = colorL, strokeWidth = thickPx,
                        start = Offset(x0 + diameter / 2 - x1, y0),
                        end = Offset(x0 + diameter / 2 - x1, y0 - y1)
                    )
                    drawLine(
                        color = colorL, strokeWidth = thickPx,
                        start = Offset(x0 + diameter / 2 + x1, y0),
                        end = Offset(x0 + diameter / 2 + x1, y0 - y1)
                    )
                    drawLine(
                        color = colorL, strokeWidth = widthPx2,
                        start = Offset(x0 + diameter / 2 - x2, y0 - y2),
                        end = Offset(x0 + diameter / 2 - x2, y0 - y21)
                    )
                    drawLine(
                        color = colorL, strokeWidth = widthPx2,
                        start = Offset(x0 + diameter / 2 + x2, y0 - y2),
                        end = Offset(x0 + diameter / 2 + x2, y0 - y21)
                    )
                    drawLine(
                        color = colorL, strokeWidth = widthPx2,
                        start = Offset(x0 + diameter / 2 - x2, y0 - y2),
                        end = Offset(x0 + diameter / 2 + x2, y0 - y2)
                    )
                }
            }
        )
    }
    @Composable fun DistanceOnly(color: Color = color()){
        val style = TextStyle(fontSize = fontSize, color = color, fontWeight = FontWeight.Bold)
        val textMeasurer = rememberTextMeasurer()
        val width = 30.dp
        val height = 30.dp
        Spacer(modifier = Modifier
            .width(width)
            .height(height)
            .drawWithCache {
                onDrawWithContent {
                    val xPx = width.toPx()
                    val yPx = (height * 0.7f).toPx()
                    val delta1 = xPx * 0.18f
                    val thickPx = 1.dp.toPx()
                    drawLine(
                        color = color, strokeWidth = thickPx,
                        start = Offset(delta1, yPx), end = Offset(xPx - delta1, yPx)
                    )
                    drawLine(
                        color = color, strokeWidth = thickPx * 2,
                        start = Offset(delta1, yPx - thickPx * 4),
                        end = Offset(delta1, yPx)
                    )
                    drawLine(
                        color = color, strokeWidth = thickPx,
                        start = Offset(xPx / 2, yPx - thickPx * 3),
                        end = Offset(xPx / 2, yPx)
                    )
                    drawLine(
                        color = color, strokeWidth = thickPx * 2,
                        start = Offset(xPx - delta1, yPx - thickPx * 4),
                        end = Offset(xPx - delta1, yPx)
                    )
                    val textLayout: TextLayoutResult =
                        textMeasurer.measure(text = AnnotatedString("km"), style = style)
                    drawText(
                        textLayout, topLeft = Offset(
                            (xPx - textLayout.size.width)/2,
                            yPx - textLayout.size.height * 1.3f
                        )
                    )
                }
            }
        )
    }
    @Composable fun CountOnly(color: Color = color()){
        val fontSize = 10.sp
        val width = 30.dp
        val height = 30.dp
        val textMeasurer = rememberTextMeasurer()
        val condensed = FontFamily( Font(com.count_out.presentation.R.font.robotocondensed_regular))
        Spacer(modifier = Modifier
            .width(width)
            .height(height)
            .drawWithCache {
                onDrawWithContent {
                    val one = textMeasurer.measure(text = AnnotatedString("1.2.3"),
                        style = TextStyle(fontSize = fontSize,color = color, fontFamily = condensed, letterSpacing = (-0.5).sp))
                    drawText(one, topLeft = Offset(3f, (height.toPx() - one.size.height) * 0.5f))

                }
            }
        )
    }
    @Composable fun DurationOnly(color: Color = color(),) {
        val width = 30.dp
        val height = 30.dp
        Spacer(modifier = Modifier
            .width(width)
            .height(height)
            .drawWithCache {
                onDrawWithContent {
                    val xPx = width.toPx()
                    val yPx = height.toPx() * 0.67f
                    val thickPx = 1.dp.toPx()
                    val widthPx2 = 2.dp.toPx()

                    val diameter = yPx * 0.7f
                    val diameter1 = diameter * 0.15f
                    val x0 = xPx / 2 - diameter / 2
                    val y0 = yPx - diameter * 0.7f
                    val metka = diameter / 8
                    val arrow = diameter / 4
                    val x1 = diameter / 8
                    val y1 = diameter / 5
                    val x2 = diameter / 4
                    val y2 = diameter / 3
                    val y21 = diameter / 10
//                    drawOval(
//                        color = colorL, style = Stroke(width = thickPx),
//                        topLeft = Offset(x = thickPx / 2, y = thickPx / 2),
//                        size = Size(xPx - thickPx, yPx0 - thickPx)
//                    )
                    drawOval(
                        color = color, style = Stroke(width = widthPx2),
                        topLeft = Offset(x = x0, y = y0),
                        size = Size(diameter, diameter)
                    )
                    drawLine(
                        color = color, strokeWidth = thickPx,
                        start = Offset(x0 + diameter / 2, y0 + diameter - metka),
                        end = Offset(x0 + diameter / 2, y0 + diameter)
                    )
                    drawLine(
                        color = color, strokeWidth = thickPx,
                        start = Offset(x0 + diameter / 2, y0),
                        end = Offset(x0 + diameter / 2, y0 + metka)
                    )
                    drawLine(
                        color = color, strokeWidth = thickPx,
                        start = Offset(x0, y0 + diameter / 2),
                        end = Offset(x0 + metka, y0 + diameter / 2)
                    )
                    drawLine(
                        color = color, strokeWidth = thickPx,
                        start = Offset(x0 + diameter, y0 + diameter / 2),
                        end = Offset(x0 + diameter - metka, y0 + diameter / 2)
                    )
                    drawLine(
                        color = color, strokeWidth = thickPx,
                        start = Offset(x0 + diameter / 2, y0 + diameter / 2),
                        end = Offset(x0 + diameter / 2 + arrow, y0 + diameter / 2 - arrow)
                    )
                    drawOval(
                        color = color,
                        topLeft = Offset(
                            x0 + (diameter - diameter1) / 2,
                            y0 + (diameter - diameter1) / 2
                        ),
                        size = Size(diameter1, diameter1)
                    )
                    drawLine(
                        color = color, strokeWidth = thickPx,
                        start = Offset(x0 + diameter / 2 - x1, y0),
                        end = Offset(x0 + diameter / 2 - x1, y0 - y1)
                    )
                    drawLine(
                        color = color, strokeWidth = thickPx,
                        start = Offset(x0 + diameter / 2 + x1, y0),
                        end = Offset(x0 + diameter / 2 + x1, y0 - y1)
                    )
                    drawLine(
                        color = color, strokeWidth = widthPx2,
                        start = Offset(x0 + diameter / 2 - x2, y0 - y2),
                        end = Offset(x0 + diameter / 2 - x2, y0 - y21)
                    )
                    drawLine(
                        color = color, strokeWidth = widthPx2,
                        start = Offset(x0 + diameter / 2 + x2, y0 - y2),
                        end = Offset(x0 + diameter / 2 + x2, y0 - y21)
                    )
                    drawLine(
                        color = color, strokeWidth = widthPx2,
                        start = Offset(x0 + diameter / 2 - x2, y0 - y2),
                        end = Offset(x0 + diameter / 2 + x2, y0 - y2)
                    )
                }
            }
        )
    }
    @Composable fun ArrowChordCanvas(progress: Float = 0f, onClick: ()->Unit = {}) {
        val radius: Dp = width/2 - 5.dp
        val arrowCount = 4
        val shaftLength = 4f
        val arrowHeadSize = 18f
        val chordAngleOffset: Float = (2 * PI / 30).toFloat()
        val color: Color =  color()

        Box(modifier = Modifier.width(width).height(height).clickable { onClick() }) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val canvasWidth = width.toPx()
                val canvasHeight = height.toPx()

                val cx = canvasWidth / 2
                val cy = canvasHeight / 2
                val r = radius.toPx()
                val strokeWidth: Float = 2.dp.toPx()
                // Длина линии (равна длине окружности)

                val lineLength = 2 * r
                val lineStartX = cx - lineLength / 2
                val lineEndX = cx + lineLength / 2
                val thickPx = thick.toPx()

                drawRoundRect(
                    color = color,
                    topLeft = Offset(x = thickPx / 2, y = thickPx / 2),
                    size = Size(canvasWidth - thickPx, canvasHeight - thickPx),
                    cornerRadius = CornerRadius(8f, 8f),
                    style = Stroke(width = thickPx),
                )
                for (i in 0 until arrowCount) {
                    val angleStart = (2 * PI * i / arrowCount).toFloat()
                    val angleEnd = angleStart + chordAngleOffset

                    // Точки на окружности
                    val circleStartX = cx + r * cos(angleStart)
                    val circleStartY = cy + r * sin(angleStart)
                    val circleTipX = cx + r * cos(angleEnd)
                    val circleTipY = cy + r * sin(angleEnd)

                    // Точки на прямой (равномерно по длине линии)
                    val t = i.toFloat() / (arrowCount - 1)
                    val lineStartXPos = lineStartX + t * lineLength/PI
                    val lineTipXPos = lineStartX + (t + chordAngleOffset / (2 * PI)) * lineLength
                    val lineY = cy

                    // Интерполяция точек
                    val startX = lerp(circleStartX, lineStartXPos.toFloat(), progress)
                    val startY = lerp(circleStartY, lineY, progress)
                    val tipX = lerp(circleTipX, lineTipXPos.toFloat(), progress)
                    val tipY = lerp(circleTipY, lineY, progress)

                    val dx = tipX - startX
                    val dy = tipY - startY
                    val chordLength = sqrt(dx * dx + dy * dy)

                    if (chordLength < shaftLength) continue

                    val nx = dx / chordLength
                    val ny = dy / chordLength

                    // Наконечник стрелки
                    val k1 = 0.8f
                    val an1 = 0.5f

                    drawLine(
                        color = color,
                        start = Offset(tipX, tipY),
                        end = Offset(
                            tipX - arrowHeadSize * (nx * k1 + ny * an1),
                            tipY - arrowHeadSize * (ny * k1 - nx * an1)
                        ),
                        strokeWidth = strokeWidth,
                    )
                    drawLine(
                        color = color,
                        start = Offset(tipX, tipY),
                        end = Offset(
                            tipX - arrowHeadSize * (nx * k1 - ny * an1),
                            tipY - arrowHeadSize * (ny * k1 + nx * an1)
                        ),
                        strokeWidth = strokeWidth,
                    )
                }

                // Рисуем контур: окружность → линия
                if (progress < 1f) {
                    drawCircle(
                        color = color,
                        center = Offset(cx, cy),
                        radius = r * (1 - progress),
                        style = Stroke(strokeWidth)
                    )
                } else {
                    drawLine(
                        color = color,
                        start = Offset(lineStartX.toFloat(), cy),
                        end = Offset(lineEndX.toFloat(), cy),
                        strokeWidth = strokeWidth
                    )
                }
            }
        }
    }

    // Линейная интерполяция
    fun lerp(a: Float, b: Float, t: Float): Float = a + (b - a) * t
    @Composable fun colorSelected(selected: Boolean, color: Color) =
        if (selected) color else Color(color.red, color.green, color.blue, color.alpha * 0.5f)
    @Composable fun styleSelected(selected: Boolean, color: Color, height: TextUnit) =
        if (selected) TextStyle(fontSize = height, color = color, fontWeight = FontWeight.Bold)
        else TextStyle(fontSize = height, color = Color(color.red, color.green, color.blue, color.alpha * 0.5f))

}


//    @Composable fun ArrowChordCanvas(selected: Boolean = false) {
//        val radius: Dp = 30.dp
//        val arrowCount: Int = 5
//        val shaftLength: Float = 4f
//        val arrowHeadSize: Float = 18f
//        val arrowStrokeWidth = 2f
//        val chordAngleOffset: Float = (2 * PI / 30).toFloat() // Δθ для хорды (в радианах)
//        val color: Color = colorSelected(selected, color())
//        Box(modifier = Modifier.fillMaxSize()) {
//            Canvas(modifier = Modifier.fillMaxSize()) {
//                val canvasWidth = size.width
//                val canvasHeight = size.height
//
//                val cx = canvasWidth / 2
//                val cy = canvasHeight / 2
//                val r = radius.toPx()
//
//                // Рисуем окружность
//                drawCircle(
//                    color = color,
//                    center = Offset(cx, cy),
//                    radius = r,
//                    style = Stroke(2f)
//                )
//
//                // Рисуем стрелки по хордам
//                for (i in 0 until arrowCount) {
//                    val angleStart = (2 * PI * i / arrowCount).toFloat() // начальный угол
//                    val angleEnd = angleStart + chordAngleOffset // конечный угол (по хорде)
//
//                    // Начальная точка хорды (основание стрелки после смещения)
//                    val startX = cx + r * cos(angleStart)
//                    val startY = cy + r * sin(angleStart)
//
//                    // Конечная точка хорды (наконечник стрелки)
//                    val tipX = cx + r * cos(angleEnd)
//                    val tipY = cy + r * sin(angleEnd)
//
//                    // Вектор хорды (от начала к наконечнику)
//                    val dx = tipX - startX
//                    val dy = tipY - startY
//                    val chordLength = sqrt(dx * dx + dy * dy)
//
//                    // Если хорда слишком короткая, пропускаем (чтобы не было артефактов)
//                    if (chordLength < shaftLength) continue
//
//                    // Нормализованный вектор хорды
//                    val nx = dx / chordLength
//                    val ny = dy / chordLength
//
//                    // Рисуем наконечник (треугольник)
//                    val k1 = 0.8f
//                    val an1 = 0.5f
//                    drawLine(
//                        color = color,
//                        start = Offset(tipX, tipY),
//                        end = Offset(
//                            tipX - arrowHeadSize * (nx * k1 + ny * an1),
//                            tipY - arrowHeadSize * (ny * k1 - nx * an1)
//                        ),
//                        strokeWidth = arrowStrokeWidth,
//                    )
//                    drawLine(
//                        color = color,
//                        start = Offset(tipX, tipY),
//                        end = Offset(
//                            tipX - arrowHeadSize * (nx * k1 - ny * an1),
//                            tipY - arrowHeadSize * (ny * k1 + nx * an1)
//                        ),
//                        strokeWidth = arrowStrokeWidth,
//                    )
//                }
//            }
//        }
//    }