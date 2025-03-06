package com.example.count_out.domain.use_case.trainings

import com.example.count_out.entity.workout.Training
import com.count_out.domain.repository.trainings.TrainingRepo
import com.example.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DeleteTrainingUC @Inject constructor(configuration: Configuration, private val repo: TrainingRepo
): UseCase<DeleteTrainingUC.Request, DeleteTrainingUC.Response>(configuration)  {
    override fun executeData(input: Request): Flow<Response> = repo.del(input.training).map { Response(it) }
    data class Request(val training: Training) : UseCase.Request
    data class Response(val training: List<Training>) : UseCase.Response
}