package com.example.count_out.ui.screens.trainings

import androidx.compose.runtime.mutableStateOf

class TrainingsConvertor @Inject constructor():
    PrimeConvertor< UseCase.Response, TrainingsState>() {

    override fun convertSuccess(data: UseCase.Response): TrainingsState {
        return TrainingsState(
            trainings = emptyList(),
            selectedId = mutableStateOf(0),
        )
    }
}