package com.count_out.presentation.view_element.icons

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.count_out.presentation.models.Dimen
import com.count_out.presentation.models.Dimen.sizeIcon
import com.count_out.presentation.view_element.TextApp

@Composable fun IconsCopy(
    onCopySet: (() -> Unit)? = null,
    onCopyExercise: (() -> Unit)? = null,
    onCopyRing: (() -> Unit)? = null,
    label: Int = 0
){
    var expanded by remember { mutableStateOf(false) }
    Box {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = Icons.Default.CopyAll,
                tint = colorScheme.outline,
                contentDescription = "" ,
                modifier = Modifier.size(sizeIcon).clickable { expanded = true })
            if ( label != 0)
                TextApp(
                    text = stringResource(label),
                    style = Dimen.typeLabel(),
                    overflow = TextOverflow.Clip,
                    modifier = Modifier.width(sizeIcon) )
        }
        MaterialTheme( shapes = shapes.copy(extraSmall = shapes.large)) {
            DropdownMenu(
                modifier = Modifier.padding(4.dp),
                expanded = expanded,
                offset = DpOffset((-8).dp, 8.dp),
                onDismissRequest = { expanded = false },
                content = { VerIcons(onCopySet, onCopyExercise, onCopyRing,) { expanded = false } })
        }
    }
}

@Composable fun VerIcons(
    onCopySet: (() -> Unit)? = null,
    onCopyExercise: (() -> Unit)? = null,
    onCopyRing: (() -> Unit)? = null,
    expanded: ()->Unit,
){
    Column( verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally){
        onCopyRing?.let { IconCopyText( text = "R"){ it(); expanded()} }
        onCopyExercise?.let { IconCopyText( text = "E"){ it(); expanded()} }
        onCopySet?.let { IconCopyText( text = "S"){ it(); expanded()} }
    }
}

@Composable fun IconCopyText(text: String = "+", onClick:()->Unit) {
    val s =4.dp
    val shapeMy = RoundedCornerShape(topStart = s, topEnd = s, bottomEnd = s, bottomStart = s)
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.padding(4.dp).size(size = sizeIcon + 4.dp)
            .border(width = 1.dp, color = colorScheme.outline, shape = shapeMy)
            .clickable { onClick() },
        content = { TextWithInlineIcon(text,Icons.Default.CopyAll)}
    )
}


@Composable @Preview fun previewCopy(){ IconCopyText("R", {})}