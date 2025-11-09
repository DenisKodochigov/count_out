package com.count_out.presentation.view_element.icons

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.count_out.presentation.models.Dimen.sizeIcon

@Composable fun IconDelText(text: String = "+", onClick:()->Unit) {
    val s =4.dp
    val shapeMy = RoundedCornerShape(topStart = s, topEnd = s, bottomEnd = s, bottomStart = s)
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.padding(4.dp).size(size = sizeIcon + 4.dp)
            .border(width = 1.dp, color = colorScheme.outline, shape = shapeMy)
            .clickable { onClick() },
        content = { TextWithInlineIcon(text,Icons.Default.DeleteOutline)}
    )
}
