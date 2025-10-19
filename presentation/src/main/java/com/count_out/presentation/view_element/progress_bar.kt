package com.count_out.presentation.view_element

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable fun ProgressBar(text: String, height: Dp = 8.dp, alignment: Alignment.Horizontal, value: Float){
    val progress by animateFloatAsState(targetValue = value)
    Column( horizontalAlignment = alignment,
        modifier = Modifier.fillMaxWidth().padding( horizontal = 12.dp)) {
        TextApp(text = text, style = typography.labelSmall)
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth().height(height),
            color = colorScheme.onPrimary,
            trackColor = colorScheme.surfaceContainer,
            strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
        )
    }
}
@Composable
fun VerticalProgress(number: Int, quantity: Int, modifier: Modifier = Modifier) {
    if (quantity >= number){
        val mProgress by animateFloatAsState(targetValue = number/quantity.toFloat())
        Column( verticalArrangement = Arrangement.SpaceAround,
            modifier = modifier.padding(start = 8.dp, end = 4.dp)
                .border(shape = RectangleShape, width = 1.dp, color = colorScheme.surfaceContainer)
                .width(6.dp))
        {
            val w1 = if ((1f - mProgress) == 0f) 0.0001f else (1f - mProgress)
//            Log.d("KDS", "VerticalProgress w1=$w1  mProgress=$mProgress  number:$number  quantity:$quantity")
            Box(modifier = Modifier.fillMaxWidth().weight(mProgress).background(colorScheme.onPrimary))
            Box(modifier = Modifier.fillMaxWidth().weight(w1).background(colorScheme.surfaceContainer))
        }
    }
}