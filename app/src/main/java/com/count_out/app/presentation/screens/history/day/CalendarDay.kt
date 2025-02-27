package com.count_out.app.presentation.screens.history.day

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.count_out.app.presentation.screens.history.HistoryScreenState
import com.count_out.app.presentation.theme.elevationNull
import com.count_out.app.presentation.theme.mTypography
import com.count_out.app.presentation.theme.shapes
import com.count_out.app.presentation.view_components.TextApp


@Composable
fun CalendarDay(uiState: HistoryScreenState, day: String = "1") {
    Card(
        elevation = elevationNull(),
        shape = shapes.extraSmall,
        border =  BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        modifier = Modifier.padding(4.dp).clickable { uiState.onClickDay(uiState.currentDay.toString()) }
    ){
        TextApp(text = uiState.currentDay.toString(), style = mTypography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 6.dp, horizontal = 8.dp).width(20.dp))
    }
}