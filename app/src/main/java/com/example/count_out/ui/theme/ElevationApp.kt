package com.example.count_out.ui.theme

import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class ElevationApp {
}
@Composable fun elevationTraining(): CardElevation {
    return CardDefaults.cardElevation(
        defaultElevation = 6.dp,
        pressedElevation= 4.dp,
        focusedElevation= 7.dp,
        hoveredElevation= 6.dp,
        draggedElevation= 4.dp,
        disabledElevation= 6.dp,
    )
}
@Composable fun elevation(): CardElevation {
    return CardDefaults.cardElevation(
        defaultElevation = 6.dp,
        pressedElevation= 4.dp,
        focusedElevation= 7.dp,
        hoveredElevation= 6.dp,
        draggedElevation= 4.dp,
        disabledElevation= 6.dp,
    )
}
@Composable fun elevationAnm(animElevation: Dp): CardElevation {
    return CardDefaults.cardElevation(
        defaultElevation = 6.dp,
        pressedElevation= animElevation,
        focusedElevation= 7.dp,
        hoveredElevation= 6.dp,
        draggedElevation= 4.dp,
        disabledElevation= 6.dp,
    )
}