package com.example.count_out.presentation.screens.trainings

import androidx.compose.runtime.mutableStateOf
import com.example.count_out.presentation.prime.PrimeConvertor
import com.example.domain.use_case.UseCase
import javax.inject.Inject

class TrainingsConvertor @Inject constructor():
    PrimeConvertor< UseCase.Response, TrainingsState>() {

    override fun convertSuccess(data: UseCase.Response): TrainingsState {
        return TrainingsState(
            trainings = emptyList(),
            selectedId = mutableStateOf(0),
        )
    }
}
