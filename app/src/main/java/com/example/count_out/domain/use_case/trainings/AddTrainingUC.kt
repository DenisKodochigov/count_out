package com.example.count_out.domain.use_case.trainings

import com.example.count_out.entity.workout.Training
import com.example.count_out.domain.repository.trainings.TrainingRepo
import com.example.count_out.domain.use_case.UseCase
import com.example.count_out.domain.use_case.UseCase1
import com.example.count_out.ui.screens.prime.PrimeConv
import com.example.count_out.ui.screens.prime.PrimeConvertor
import com.example.count_out.ui.screens.trainings.TrainingsConvertor
import com.example.count_out.ui.screens.trainings.TrainingsState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AddTrainingUC @Inject constructor(
    configuration: Configuration,
    private val repo: TrainingRepo,
): UseCase1<TrainingsState, AddTrainingUC.Request, AddTrainingUC.Response>(configuration)  {

    override fun convertor(): PrimeConv<AddTrainingUC.Response, TrainingsState> = TrainingsConvertor()

    override fun executeData(input: Request): Flow<Response> = repo.add().map { Response(it) }//.map { converter.convert(it) }
    data object Request: UseCase.Request
    data class Response(val trainings: List<Training>): UseCase.Response
}