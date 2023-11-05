package com.example.count_out.ui.view_components

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.bottomBarAnimatedScroll(height: Dp = 56.dp, offsetHeightPx: MutableState<Float>): Modifier =
    composed {
        val bottomBarHeightPx = with(LocalDensity.current) { (height + 100.dp).roundToPx().toFloat() }
        val connection = remember {
            object: NestedScrollConnection {
                override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                    val newOffset = offsetHeightPx.value + available.y
                    offsetHeightPx.value = newOffset.coerceIn(-bottomBarHeightPx, 0f)
                    return Offset.Zero
                }
            }
        }
        this.nestedScroll(connection)
    }