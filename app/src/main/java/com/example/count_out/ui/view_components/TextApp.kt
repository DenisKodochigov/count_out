package com.example.count_out.ui.view_components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun TextApp(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center,
    style: TextStyle,
    fontWeight: FontWeight = FontWeight.Normal
) {
    Text(
        text = text,
        style = style,
        maxLines = 1,
        fontWeight = fontWeight,
        overflow = TextOverflow.Ellipsis,
        textAlign = textAlign,
        modifier = modifier,
//        color = MaterialTheme.colorScheme.onSurface
    )
}
@Composable
fun TextAppColor(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center,
    style: TextStyle,
    fontWeight: FontWeight = FontWeight.Normal,
    color: Color = MaterialTheme.colorScheme.onSurface
) {
    Text(
        text = text,
        style = style,
        maxLines = 1,
        fontWeight = fontWeight,
        overflow = TextOverflow.Ellipsis,
        textAlign = textAlign,
        modifier = modifier,
        color = color
    )
}