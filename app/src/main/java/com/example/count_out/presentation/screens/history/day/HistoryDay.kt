package com.example.count_out.presentation.screens.history.day

import androidx.compose.runtime.Composable
import com.example.count_out.presentation.screens.history.HistoryScreenState


@Composable
fun HistoryDay(uiState: HistoryScreenState) {
    Title(uiState)
    ShortInfo(uiState)
}

@Composable
fun Title(uiState: HistoryScreenState) {
    DataTraining(uiState)
    WeatherTraining(uiState)
    ExerciseList(uiState)
}

@Composable
fun DataTraining(uiState: HistoryScreenState) {

}

@Composable
fun WeatherTraining(uiState: HistoryScreenState) {

}

@Composable
fun ExerciseList(uiState: HistoryScreenState) {

}

@Composable
fun ShortInfo(uiState: HistoryScreenState) {

}





