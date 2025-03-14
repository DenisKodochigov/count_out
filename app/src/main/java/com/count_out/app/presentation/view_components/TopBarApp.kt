package com.count_out.app.presentation.view_components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.count_out.presentation.view_element.TextAppEllipsis

@Composable
fun CollapsingToolbar(
    text: String,
    backScreen: ()->Unit = {},
    moreHoriz: ()->Unit = {},)
{
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier. padding(top = 50.dp, bottom = 12.dp).height(40.dp).fillMaxWidth()
//            .background(color = MaterialTheme.colorScheme.surface, shape = topBarShape)
    ){
        IconButton( onClick = backScreen ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface)
        }
        TextAppEllipsis(
            text = text,
            style = typography.headlineMedium,
            modifier = Modifier.weight(1f).fillMaxWidth()
        )
        IconButton( onClick = moreHoriz) {
            Icon(imageVector = Icons.Default.MoreHoriz,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@Preview(showBackground = true)
@Composable fun CollapsingToolbarPreview(){
    CollapsingToolbar(text = "Название экрана", {},{})
}