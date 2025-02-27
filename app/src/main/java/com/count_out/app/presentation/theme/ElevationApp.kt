package com.count_out.app.presentation.theme

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
@Composable fun elevationCalendar(): CardElevation {
    return CardDefaults.cardElevation(
        defaultElevation = 4.dp,
        pressedElevation= 2.dp,
        focusedElevation= 5.dp,
        hoveredElevation= 6.dp,
        draggedElevation= 4.dp,
        disabledElevation= 0.dp,
    )
}
@Composable fun elevationNull(): CardElevation {
    return CardDefaults.cardElevation(
        defaultElevation = 1.dp,
        pressedElevation= 1.dp,
        focusedElevation= 1.dp,
        hoveredElevation= 1.dp,
        draggedElevation= 1.dp,
        disabledElevation= 1.dp,
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