package com.count_out.domain.use_case.training

import com.count_out.domain.entity.Training
import com.count_out.domain.repository.trainings.TrainingRepo
import com.count_out.domain.use_case.UseCase
import com.count_out.domain.use_case.UseCase.Configuration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChangeNameTrainingUC @Inject constructor(
    configuration: Configuration, private val repo: TrainingRepo
): UseCase<ChangeNameTrainingUC.Request, ChangeNameTrainingUC.Response>(configuration)  {
    override fun executeData(input: Request): Flow<Response> =
        repo.update(input.training).map { Response(it) }
    data class Request(val training: Training): UseCase.Request
    data class Response(val training: Training): UseCase.Response
}