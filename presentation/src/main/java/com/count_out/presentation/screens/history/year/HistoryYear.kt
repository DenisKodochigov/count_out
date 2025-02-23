package com.count_out.presentation.screens.history.year

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.count_out.app.presentation.screens.history.HistoryScreenState
import com.count_out.app.presentation.screens.history.month.CardMonth
import com.count_out.app.ui.theme.elevationCalendar
import com.count_out.app.ui.theme.mTypography
import com.count_out.app.ui.theme.shapes
import com.count_out.app.ui.view_components.TextApp
import java.time.LocalDate

@Composable
fun HistoryYear(uiState: HistoryScreenState) {
    CardYear(uiState)
}

@Composable
fun CardYear(uiState: HistoryScreenState) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = shapes.extraSmall,
        elevation = elevationCalendar()
    ){
        for (month in 1..12) {
            TextApp(text = LocalDate.of(2024, month,1).month.name,
                modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                textAlign = TextAlign.Center,
                style = mTypography.headlineSmall)
            CardMonth(uiState, month)
        }
    }
}

@Preview
@Composable
fun PreviewHistoryYear(){
    HistoryYear(HistoryScreenState())
}