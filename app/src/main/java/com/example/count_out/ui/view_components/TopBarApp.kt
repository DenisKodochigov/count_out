package com.example.count_out.ui.view_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.count_out.ui.theme.interReg16
import com.example.count_out.ui.theme.topBarShape

@Composable
fun CollapsingToolbar(
    text: String,
    backScreen: ()->Unit = {},
    moreHoriz: ()->Unit = {},)
{
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface, shape = topBarShape)
    ){
        IconButton( onClick = backScreen ) {
            Icon(imageVector = Icons.Default.ArrowBackIos,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface)
        }
        TextApp(
            text = text,
            style = interReg16,
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