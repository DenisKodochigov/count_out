package com.example.count_out.ui.screens.history.month

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.count_out.ui.screens.history.HistoryScreenState
import com.example.count_out.ui.screens.history.day.CardDay
import com.example.count_out.ui.theme.elevationCalendar
import com.example.count_out.ui.theme.shapes
import java.time.LocalDate

@Composable
fun HistoryMonth(uiState: HistoryScreenState, month: Int) {
    CardMonth(uiState = uiState, month = month)
}
@Composable
fun CardMonth(uiState: HistoryScreenState, month: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = shapes.extraSmall,
        elevation = elevationCalendar()
    ){
        val countDay = LocalDate.of(2024, month,1).lengthOfMonth()
        val dayWeek = LocalDate.of(2024,month,1).dayOfWeek
        dayWeek.value
        var day = 1
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()){
            for (d in 1..7){
                if (dayWeek.value == d || day > 1 ) {
                    CardDay(uiState, day.toString())
                    day++
                } else CardDay(uiState,"")
            }
        }
        for (i in 1..4){
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()){
                for (d in 1..7){
                    if (day <= countDay) {
                        CardDay(uiState, day.toString())
                        day++
                    } else { CardDay(uiState, "")}
                }
            }
        }
    }
}
