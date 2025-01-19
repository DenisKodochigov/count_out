package com.example.domain.use_case

import com.example.domain.entity.Training
import com.example.domain.repository.training.TrainingRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CopyTrainingUC @Inject constructor(configuration: Configuration, private val repo: TrainingRepo
): UseCase<CopyTrainingUC.Request, CopyTrainingUC.Response>(configuration)  {
    override fun executeData(input: Request): Flow<Response> = repo.copy(input.id).map { Response(it) }
    data class Request(val id: Long) : UseCase.Request
    data class Response(val training: List<Training>) : UseCase.Response
}