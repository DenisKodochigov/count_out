package com.count_out.domain.use_case.plans

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.Training
import com.count_out.domain.repository.plans.TrainingRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTrainingUC @Inject constructor(configuration: Configuration, private val repo: TrainingRepo
): UseCase<GetTrainingUC.Request, GetTrainingUC.Response>(configuration)  {
//    override fun implementation_old(request: Request): Flow<Response> {
//        return repo.get(request.training).map { Response(it) } }
    override fun implementation(request: Request): Flow<ResultUC<Response>> =
        repo.get(request.training).map { ResultUC.Success(Response(it)) }

    data class Request(val training: Training) : UseCase.Request
    data class Response(val training: Training) : UseCase.Response
}