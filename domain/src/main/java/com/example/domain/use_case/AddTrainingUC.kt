package com.example.domain.use_case

import com.example.domain.entity.Training
import com.example.domain.repository.training.TrainingRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AddTrainingUC @Inject constructor(configuration: Configuration, private val repo: TrainingRepo
): UseCase<AddTrainingUC.Request, AddTrainingUC.Response>(configuration)  {
    override fun executeData(input: Request): Flow<Response> = repo.addCopy(input.training).map { Response(it) }
    data class Request(val training: Training): UseCase.Request
    data class Response(val training: List<Training>): UseCase.Response
}