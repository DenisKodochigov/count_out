package com.example.domain.use_case.trainings

import com.example.domain.entity.Training
import com.example.domain.repository.trainings.TrainingRepo
import com.example.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DelTrainingUC @Inject constructor(configuration: Configuration, private val repo: TrainingRepo
): UseCase<DelTrainingUC.Request, DelTrainingUC.Response>(configuration)  {
    override fun executeData(input: Request): Flow<Response> = repo.del(input.training).map { Response(it) }
    data class Request(val training: Training) : UseCase.Request
    data class Response(val training: List<Training>) : UseCase.Response
}