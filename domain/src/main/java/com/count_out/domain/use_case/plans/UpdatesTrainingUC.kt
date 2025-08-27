package com.count_out.domain.use_case.plans

import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.Training
import com.count_out.domain.repository.plans.TrainingRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdatesTrainingUC @Inject constructor(
    configuration: Configuration, private val repo: TrainingRepo
): UseCase<UpdatesTrainingUC.Request, UpdatesTrainingUC.Response>(configuration)  {

    override fun method(request: Request): Flow<ResultUC<TypeRepo>> =
        repo.updates(TypeRepo.PlanT(request.training))
    override fun response(typeRepo: TypeRepo): Response = Response(typeRepo)
    data class Request(val training: Training): UseCase.Request
    data class Response(val training: TypeRepo): UseCase.Response
}
