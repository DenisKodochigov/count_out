package com.count_out.domain.use_case.trainings

import com.count_out.domain.entity.workout.Training
import com.count_out.domain.repository.trainings.TrainingRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTrainingUC @Inject constructor(configuration: Configuration, private val repo: TrainingRepo
): UseCase<GetTrainingUC.Request, GetTrainingUC.Response>(configuration)  {
    override fun executeData(input: Request): Flow<Response> = repo.get(input.id).map { Response(it) }
    data class Request(val id: Long) : UseCase.Request
    data class Response(val training: Training) : UseCase.Response
}