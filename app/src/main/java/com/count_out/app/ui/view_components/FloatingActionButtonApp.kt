package com.count_out.app.ui.view_components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.count_out.app.ui.theme.alumBodyMedium

@Composable fun ExtendedFAB(
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Filled.Add,
    onClick: () -> Unit,
    textId: Int
){
    ExtendedFloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        icon = { IconFab(icon = icon) },
        text = { TextApp(text = stringResource(id = textId), style = alumBodyMedium) },
    )
}
@Composable fun IconFab(icon: ImageVector){
    Icon(
        imageVector = icon,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.onPrimaryContainer,
    )
}


