package com.count_out.presentation.view_element

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable fun ProgressBar(text: String, height: Dp = 8.dp, alignment: Alignment.Horizontal, value: Float){
    val progress by animateFloatAsState(targetValue = value)
    Column( horizontalAlignment = alignment,
        modifier = Modifier.fillMaxWidth().padding( horizontal = 12.dp)) {
        TextApp(text = text, style = typography.labelSmall,)
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth().height(height),
            color = colorScheme.onPrimary,
            trackColor = colorScheme.surfaceContainer,
            strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
        )
    }
}