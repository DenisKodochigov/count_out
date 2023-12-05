package com.example.count_out.ui.view_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.count_out.entity.SizeElement
import com.example.count_out.entity.TypeText
import com.example.count_out.ui.theme.colorApp
import com.example.count_out.ui.theme.sizeApp
import com.example.count_out.ui.theme.styleApp

@Composable fun ExtendedFAB(
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Filled.Add,
    onClick: () -> Unit,
    text: String
){
    ExtendedFloatingActionButton(
        modifier = modifier,
        onClick = onClick,
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = colorApp.onPrimaryContainer,
                modifier = Modifier
                    .padding(sizeApp(SizeElement.PADDING_FAB))
                    .background(color = colorApp.primaryContainer)
            )
        },
        text = { TextApp(text = text, style = styleApp(nameStyle = TypeText.EDIT_TEXT)) },
    )
}
//@Composable
//fun FloatingActionButtonApp( offset: Dp, modifier: Modifier = Modifier,
//                             icon: ImageVector, onClick: () -> Unit)
//{
//    FloatingActionButton(
//        onClick = onClick,
//        modifier = modifier
//            .offset(0.dp, offset)
//            .size(sizeApp(SizeElement.SIZE_FAB))
//            .testTag(FAB_PLUS))
//    {
//        Icon(
//            icon, null,
//            tint = MaterialTheme.colorScheme.onPrimaryContainer,
//            modifier = Modifier
//                .padding(sizeApp(SizeElement.PADDING_FAB))
//                .fillMaxSize()
//                .background(color = MaterialTheme.colorScheme.primaryContainer)
//        )
//    }
//}
//@Composable
//fun FabAnimation(show: Boolean, offset: Dp, icon: ImageVector, onClick: () -> Unit) {
//
//    var isAnimated by rememberSaveable { mutableStateOf(false) }
//    val refreshScreen = remember{ mutableStateOf(true)}
//    val offsetFAB by animateDpAsState(
//        targetValue = if (isAnimated) {
//            if (show) offset else sizeApp(SizeElement.HEIGHT_FAB_BOX)
//        } else {
//            if (show) sizeApp(SizeElement.HEIGHT_FAB_BOX) else offset
//        },
//        animationSpec = tween(durationMillis = 600), label = ""
//    )
//
//    FloatingActionButtonApp(offset = offsetFAB,icon = icon, onClick = onClick)
//    isAnimated = true
//}