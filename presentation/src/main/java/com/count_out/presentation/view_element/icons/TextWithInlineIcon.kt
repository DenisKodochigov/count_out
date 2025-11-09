package com.count_out.presentation.view_element.icons

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.TextUnit
import com.count_out.presentation.models.Dimen.sizeIcon

@Composable
fun TextWithInlineIcon(text: String, imageVector: ImageVector? = null, image: Int? = null) {
    val style = MaterialTheme.typography.headlineMedium
    val size: TextUnit = style.fontSize/2
    val iconId = "iconInline"
    val text = buildAnnotatedString {
        append(text)
        appendInlineContent(iconId, "[icon]")
    }
    val inlineContent = mapOf(
        iconId to InlineTextContent( Placeholder(width = size, height = size,
            placeholderVerticalAlign = PlaceholderVerticalAlign.AboveBaseline)
        ) {
            imageVector?.let {imageVec->
                Icon(
                    imageVector = imageVec,
                    contentDescription = null,
                    tint = colorScheme.outline,
                    modifier = Modifier.size(sizeIcon)
                )
            } ?:
            image?.let{ imageInt->
                Icon(
                    painter = painterResource(id = imageInt),
                    contentDescription = null,
                    tint = colorScheme.outline,
                    modifier = Modifier.size(sizeIcon)
                )
            }
        }
    )
    Text(text = text, inlineContent = inlineContent, style = style, color = colorScheme.outline)
}


