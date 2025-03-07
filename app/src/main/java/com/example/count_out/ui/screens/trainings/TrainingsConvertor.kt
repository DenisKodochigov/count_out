package com.example.count_out.ui.screens.trainings

import androidx.compose.runtime.mutableStateOf
import com.example.count_out.domain.use_case.trainings.AddTrainingUC
import com.example.count_out.domain.use_case.trainings.GetTrainingsUC
import com.example.count_out.ui.screens.prime.PrimeConv
import com.example.count_out.ui.screens.prime.PrimeConvertor
import javax.inject.Inject

class TrainingsConvertor @Inject constructor(): PrimeConv<AddTrainingUC.Response, TrainingsState> {
    override fun convertSuccess(data: AddTrainingUC.Response): TrainingsState {
        return TrainingsState(
            trainings = data.trainings,
            selectedId = mutableStateOf(0),
        )
    }
}

class GetTrainingsConvertor @Inject constructor():
    PrimeConvertor< GetTrainingsUC.Response, TrainingsState>() {
    override fun convertSuccess(data: GetTrainingsUC.Response): TrainingsState {
        return TrainingsState(
            trainings = data.trainings,
            selectedId = mutableStateOf(0),
        )
    }
}