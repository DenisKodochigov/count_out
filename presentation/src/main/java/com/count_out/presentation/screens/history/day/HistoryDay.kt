package com.count_out.app.presentation.screens.history.day

import androidx.compose.runtime.Composable
import com.count_out.presentation.screens.history.HistoryState


@Composable
fun HistoryDay(uiState: HistoryState) {
    Title(uiState)
    ShortInfo(uiState)
}

@Composable
fun Title(uiState: HistoryState) {
    DataTraining(uiState)
    WeatherTraining(uiState)
    ExerciseList(uiState)
}

@Composable
fun DataTraining(uiState: HistoryState) {

}

@Composable
fun WeatherTraining(uiState: HistoryState) {

}

@Composable
fun ExerciseList(uiState: HistoryState) {

}

@Composable
fun ShortInfo(uiState: HistoryState) {

}





