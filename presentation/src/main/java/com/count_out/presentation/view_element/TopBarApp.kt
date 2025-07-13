package com.count_out.presentation.view_element

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CollapsingToolbar(
    text: String,
    selected: Boolean = false,
    backScreen: ()->Unit = {},
    onClickText: ()->Unit = {},)
{
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 35.dp, bottom = 12.dp, end = 30.dp).height(40.dp).fillMaxWidth()
//            .background(color = MaterialTheme.colorScheme.surface, shape = topBarShape)
    ){
        IconButton( onClick = backScreen ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline)
        }
        TextAppEllipsis(
            text = text,
            selected = selected,
            style = typography.headlineMedium,
            modifier = Modifier.weight(1f).fillMaxWidth().clickable(enabled = true, onClick = { onClickText()})
        )
//        IconButton( onClick = moreHoriz) {
//            Icon(imageVector = Icons.Default.MoreHoriz,
//                contentDescription = null,
//                tint = MaterialTheme.colorScheme.onSurface)
//        }
    }
}
@Composable fun TopBarApp(
    text: String,
    selected: Boolean = false,
    onClickText: ()->Unit = {},)
{
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 0.dp, bottom = 0.dp).fillMaxWidth()
    ){
        TextAppEllipsis(
            text = text.replaceFirstChar { it.uppercase() },
            selected = selected,
            style = typography.headlineMedium,
            modifier = Modifier.weight(1f).fillMaxWidth().clickable(enabled = true, onClick = { onClickText()})
        )
    }
}

@Preview(showBackground = true)
@Composable fun CollapsingToolbarPreview(){
    CollapsingToolbar(text = "Название экрана", false,{},{})
}