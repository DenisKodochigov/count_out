//package com.count_out.app.presentation.screens.trainings
//
//import androidx.compose.runtime.mutableStateOf
//import com.count_out.app.presentation.prime.PrimeConvertor
//import com.count_out.domain.use_case.UseCase
//import javax.inject.Inject
//
//class TrainingsConvertor @Inject constructor():
//    PrimeConvertor< UseCase.Response, TrainingsState>() {
//
//    override fun convertSuccess(data: UseCase.Response): TrainingsState {
//        return TrainingsState(
//            trainings = emptyList(),
//            selectedId = mutableStateOf(0),
//        )
//    }
//}
