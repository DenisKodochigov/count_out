package com.count_out.domain.use_case.trainings

import com.count_out.domain.entity.workout.Training
import com.count_out.domain.repository.trainings.TrainingRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SelectTrainingUC @Inject constructor(
    configuration: Configuration, private val repo: TrainingRepo
): UseCase<SelectTrainingUC.Request, SelectTrainingUC.Response>(configuration)  {
    override fun executeData(input: Request): Flow<Response> = repo.select(input.training).map { Response(it) }
    data class Request(val training: Training): UseCase.Request
    data class Response(val trainings: List<Training>): UseCase.Response
}