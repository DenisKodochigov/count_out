package com.count_out.presentation.view_element

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontVariation.weight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.count_out.presentation.view_element.custom_view.Frame

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
            modifier = modifier
                .border(shape = RectangleShape, width = 1.dp, color = colorScheme.surfaceContainer)
                .width(6.dp))
        {
            val w1 = if ((1f - mProgress) == 0f) 0.0001f else (1f - mProgress)
            Log.d("KDS", "VerticalProgress w1=$w1  mProgress=$mProgress")
            Box(modifier = Modifier.fillMaxWidth().fillMaxHeight().weight(w1).background(Color.Cyan))
            Box(modifier = Modifier.fillMaxWidth().fillMaxHeight().weight(mProgress).background(Color.Blue))
        }
    }
}