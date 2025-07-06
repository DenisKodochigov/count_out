package com.count_out.domain.use_case.plans

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.Training
import com.count_out.domain.repository.plans.TrainingRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UpdatesTrainingUC @Inject constructor(
    configuration: Configuration, private val repo: TrainingRepo
): UseCase<UpdatesTrainingUC.Request, UpdatesTrainingUC.Response>(configuration)  {

    override fun implementation(request: Request): Flow<ResultUC<Response>> =
        repo.updates(request.training).map { ResultUC.Success(Response(it)) }

    data class Request(val training: Training): UseCase.Request
    data class Response(val training: List<Training>): UseCase.Response
}