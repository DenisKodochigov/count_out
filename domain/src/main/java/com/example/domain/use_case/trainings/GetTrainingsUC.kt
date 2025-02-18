package com.example.domain.use_case.trainings

import com.example.domain.entity.Training
import com.example.domain.repository.trainings.TrainingRepo
import com.example.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTrainingsUC @Inject constructor(
    configuration: Configuration, private val repo: TrainingRepo
): UseCase<GetTrainingsUC.Request, GetTrainingsUC.Response>(configuration)  {
    override fun executeData(input: Request): Flow<Response> = repo.gets().map { Response(it) }
    data object Request : UseCase.Request
    data class Response(val trainings: List<Training>): UseCase.Response
}