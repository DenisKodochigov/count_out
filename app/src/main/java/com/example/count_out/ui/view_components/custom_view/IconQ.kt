package com.example.count_out.ui.view_components.custom_view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.count_out.ui.view_components.lg

@Preview
@Composable fun Preview() { IconQ.Distance()}

object IconQ{
    private val fontSize = 12.sp
    private val width = 39.dp
    private val height = 39.dp
    private val thick = 1.dp
    private const val strokeWidth = 3f
    private const val arrowWidthHor = 2f
    private const val deltaUpDownY = 4f
    private const val deltaUpDownX = 1f
    private const val deltaLeftRightY = 1f
    private const val deltaLeftRightX = 4f
    private const val heightHor = 20f
    private const val widthHor = 10f
    private const val heightVer = 10f
    private const val widthVer = 40f

    fun text(){}
    @Composable fun ArrowLeft() {
        val colorLine = MaterialTheme.colorScheme.primary
        Spacer(modifier = Modifier
            .height(Dp(heightHor))
            .width(Dp(widthHor))
            .drawWithCache {
                onDrawWithContent {
                    drawLine(
                        color = colorLine,
                        strokeWidth = arrowWidthHor,
                        start = Offset(x = deltaLeftRightX, y = size.height / 2 + deltaLeftRightY),
                        end = Offset(x = size.width - deltaLeftRightX, y = 0f + deltaLeftRightY),
                    )
                    drawLine(
                        color = colorLine,
                        strokeWidth = arrowWidthHor,
                        start = Offset(x = deltaLeftRightX, y = size.height / 2 - deltaLeftRightY),
                        end = Offset(
                            x = size.width - deltaLeftRightX,
                            y = size.height - deltaLeftRightY
                        ),
                    )
                }
            }
        )
    }
    @Composable fun ArrowRight() {
        val colorLine = MaterialTheme.colorScheme.primary
        Spacer(modifier = Modifier
            .height(Dp(heightHor))
            .width(Dp(widthHor))
            .drawWithCache {
                onDrawWithContent {
                    drawLine(
                        color = colorLine,
                        strokeWidth = arrowWidthHor,
                        start = Offset(
                            x = size.width - deltaLeftRightX,
                            y = size.height / 2 + deltaLeftRightY
                        ),
                        end = Offset(x = deltaLeftRightX, y = 0f + deltaLeftRightY),
                    )
                    drawLine(
                        color = colorLine,
                        strokeWidth = arrowWidthHor,
                        start = Offset(
                            x = size.width - deltaLeftRightX,
                            y = size.height / 2 - deltaLeftRightY
                        ),
                        end = Offset(x = deltaLeftRightX, y = size.height - deltaLeftRightY),
                    )
                }
            }
        )
    }
    @Composable fun ArrowUp() {
        val colorLine = MaterialTheme.colorScheme.primary

        Spacer(modifier = Modifier
            .height(Dp(heightHor))
            .width(Dp(widthVer))
            .drawWithCache {
                onDrawWithContent {
                    drawLine(
                        color = colorLine,
                        strokeWidth = strokeWidth,
                        start = Offset(x = 0f + deltaUpDownX, y = size.height - deltaUpDownY),
                        end = Offset(x = size.width / 2 + deltaUpDownX, y = 0f + deltaUpDownY),
                    )

                    drawLine(
                        color = colorLine,
                        strokeWidth = strokeWidth,
                        start = Offset(x = size.width / 2 - deltaUpDownX, y = 0f + deltaUpDownY),
                        end = Offset(x = size.width - deltaUpDownX, y = size.height - deltaUpDownY),
                    )
                }
            }
        )
    }
    @Composable fun ArrowDown() {
        val colorLine = MaterialTheme.colorScheme.primary

        Spacer(modifier = Modifier
            .height(Dp(heightHor))
            .width(Dp(widthVer))
            .drawWithCache {
                onDrawWithContent {
                    drawLine(
                        color = colorLine,
                        strokeWidth = strokeWidth,
                        start = Offset(x = 0f + deltaUpDownX, y = 0f + deltaUpDownY),
                        end = Offset(
                            x = size.width / 2 + deltaUpDownX,
                            y = size.height - deltaUpDownY
                        ),
                    )
                    drawLine(
                        color = colorLine,
                        strokeWidth = strokeWidth,
                        start = Offset(
                            x = size.width / 2 - deltaUpDownX,
                            y = size.height - deltaUpDownY
                        ),
                        end = Offset(x = size.width - deltaUpDownX, y = 0f + deltaUpDownY),
                    )
                }
            }
        )
    }
    @Composable fun ArrowNoneVer(){
        Canvas(modifier = Modifier
            .height(Dp(heightVer))
            .width(Dp(widthVer))) {}
    }
    @Composable fun ArrowNoneHor(){
        Canvas(modifier = Modifier
            .height(Dp(heightHor))
            .width(Dp(widthHor))) {}
    }
    @Composable fun color() = MaterialTheme.colorScheme.outline
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
                    onDrawWithContent {
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
            }
        )
    }
    @Composable fun Max(color: Color = color(), onClick: ()->Unit = {}){
        val textMeasurer = rememberTextMeasurer()
        Spacer(modifier = Modifier
            .width(width)
            .height(height)
            .clickable { onClick() }
            .drawWithCache {
                onDrawWithContent {
                    val xPx = width.toPx()
                    val yPx = height.toPx()
                    val heightText = 5.sp
                    val radius = 3
                    val thickPx = thick.toPx()
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
    @Composable fun Min(color: Color = color(), onClick: ()->Unit = {}){
        val textMeasurer = rememberTextMeasurer()
        Spacer(modifier = Modifier
            .width(width)
            .height(height)
            .clickable { onClick() }
            .drawWithCache {
                onDrawWithContent {
                    val xPx = width.toPx()
                    val yPx = height.toPx()
                    val heightText = 5.sp
                    val radius = 3
                    val thickPx = thick.toPx()
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
                val xPx = width.toPx()
                val yPx = height.toPx()
                val thickPx = thick.toPx()
                onDrawWithContent {
                    drawOval(
                        color = color,
                        style = Stroke(width = thickPx),
                        topLeft = Offset(x = thickPx / 2, y = thickPx / 2),
                        size = Size(xPx - thickPx, yPx - thickPx)
                    )
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
                onDrawWithContent {
                    drawOval(
                        color = color,
                        style = Stroke(width = thickPx),
                        topLeft = Offset(x = thickPx / 2, y = thickPx / 2),
                        size = Size(xPx - thickPx, yPx - thickPx)
                    )
                    drawPath(color = color, path = triangle, style = Stroke(width = thickPx))
                }
            }
        )
    }
    @Composable fun Stop( color: Color = color(), onClick: ()->Unit = {}){
        Spacer(modifier = Modifier
            .width(width)
            .height(height)
            .clickable { onClick() }
            .drawWithCache {
                val xPx = width.toPx()
                val yPx = height.toPx()
                val thickPx = thick.toPx()

                onDrawWithContent {
                    drawOval(
                        color = color,
                        style = Stroke(width = thickPx),
                        topLeft = Offset(x = thickPx / 2, y = thickPx / 2),
                        size = Size(xPx - thickPx, yPx - thickPx)
                    )
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
    @Composable fun Multi( color: Color = color(), onClick: ()->Unit = {}){
        Spacer(modifier = Modifier
            .width(width)
            .height(height)
            .clickable { onClick() }
            .drawWithCache {
                val xPx = width.toPx()
                val yPx = height.toPx()
                val thickPx = thick.toPx()
                val diameter = 5f
                val x0 = xPx / 2 - thickPx * diameter / 2
                val y0 = yPx / 2 - thickPx * diameter / 2
                val delta1 = yPx * 0.2f

                onDrawWithContent {
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
    @Composable fun Collapsing( color: Color = color(), onClick: ()->Unit = {}){
        Spacer(modifier = Modifier
            .width(width)
            .height(height)
            .clickable { onClick() }
            .drawWithCache {
                val xPx = width.toPx()
                val yPx = height.toPx()
                val thickPx = thick.toPx()
                onDrawWithContent {
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
    @Composable fun UnCollapsing( color: Color = color(), onClick: ()->Unit = {}){
        Spacer(modifier = Modifier
            .width(width)
            .height(height)
            .clickable { onClick() }
            .drawWithCache {
                val xPx = width.toPx()
                val yPx = height.toPx()
                val thickPx = thick.toPx()
                onDrawWithContent {
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
    @Composable fun Copy( color: Color = color(), onClick: ()->Unit = {}){
        Spacer(modifier = Modifier
            .width(width)
            .height(height)
            .clickable { onClick() }
            .drawWithCache {
                val xPx = width.toPx()
                val yPx = height.toPx()
                val thickPx = thick.toPx()
                onDrawWithContent {
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
    @Composable fun HorLine( color: Color = color(), onClick: ()->Unit = {}){
        Spacer(modifier = Modifier
            .width(width)
            .height(height)
            .clickable { onClick() }
            .drawWithCache {
                val xPx = width.toPx()
                val yPx = height.toPx()
                val thickPx = thick.toPx()
                onDrawWithContent {
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
    @Composable fun Mark( color: Color = color(), onClick: ()->Unit = {}){
        Spacer(modifier = Modifier
            .width(width)
            .height(height)
            .clickable { onClick() }
            .drawWithCache {
                val xPx = width.toPx()
                val yPx = height.toPx()
                val thickPx = thick.toPx()
                onDrawWithContent {
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
    @Composable fun Distance(selected: Boolean = false, color: Color = color(), onClick: ()->Unit = {}){
        val colorL = colorSelected(selected, color)
        val style = styleSelected (selected, color, fontSize)
        val textMeasurer = rememberTextMeasurer()
        Spacer(modifier = Modifier
            .width(width)
            .height(height)
            .clickable { onClick() }
            .drawWithCache {
                val xPx = width.toPx()
                val yPx = (height * 0.7f).toPx()
                val yPx0 = (height).toPx()
                val delta1 = xPx * 0.18f
                val thickPx = 1.dp.toPx()
                onDrawWithContent {
                    drawOval(
                        color = colorL, style = Stroke(width = thickPx),
                        topLeft = Offset(x = thickPx / 2, y = thickPx / 2),
                        size = Size(xPx - thickPx, yPx0 - thickPx)
                    )
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
                    lg("textLayout.size ${textLayout.size} xPx:$xPx")
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
                    drawOval(
                        color = colorL, style = Stroke(width = thickPx),
                        topLeft = Offset(x = thickPx / 2, y = thickPx / 2),
                        size = Size(xPx - thickPx, yPx - thickPx)
                    )
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
    @Composable fun Duration( selected: Boolean = false, onClick: ()->Unit = {},
                                  color: Color = color(),) {
        val colorL = colorSelected(selected, color)
        Spacer(modifier = Modifier
            .width(width)
            .height(height)
            .clickable { onClick() }
            .drawWithCache {
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

                onDrawWithContent {
                    drawOval(
                        color = colorL, style = Stroke(width = thickPx),
                        topLeft = Offset(x = thickPx / 2, y = thickPx / 2),
                        size = Size(xPx - thickPx, yPx0 - thickPx)
                    )
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
    @Composable fun colorSelected(selected: Boolean, color: Color) =
        if (selected) color else Color(color.red, color.green, color.blue, color.alpha * 0.5f)
    @Composable fun styleSelected(selected: Boolean, color: Color, height: TextUnit) =
        if (selected) TextStyle(fontSize = height, color = color, fontWeight = FontWeight.Bold)
        else TextStyle(fontSize = height, color = Color(color.red, color.green, color.blue, color.alpha * 0.5f))
}