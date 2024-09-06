package com.example.count_out.ui.view_components

import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import com.example.count_out.ui.theme.shapes

@OptIn(ExperimentalMaterial3Api::class)
@Composable fun ModalBottomSheetApp(
    onDismissRequest: ()->Unit,
    modifier:Modifier = Modifier,
    shape: Shape = shapes.small,
    sheetState: SheetState,
    content: @Composable ()->Unit,
) {

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        shape = shape,
        containerColor = BottomSheetDefaults.ContainerColor,
        contentColor = contentColorFor(BottomAppBarDefaults.containerColor),
        tonalElevation = BottomSheetDefaults.Elevation,
        scrimColor = BottomSheetDefaults.ScrimColor,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        contentWindowInsets = {BottomSheetDefaults.windowInsets},
        sheetState = sheetState,
        content = { content() })
}