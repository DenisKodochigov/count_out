package com.count_out.domain.use_case.plans

import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.Plan
import com.count_out.domain.repository.plans.PlanRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdatesTrainingUC @Inject constructor(
    configuration: Configuration, private val repo: PlanRepo
): UseCase<UpdatesTrainingUC.Request, UpdatesTrainingUC.Response>(configuration)  {

    override fun method(request: Request): Flow<ResultUC<TypeRepo>> =
        repo.update(TypeRepo.PlanT(request.plan))
    override fun response(typeRepo: TypeRepo): Response = Response(typeRepo)
    data class Request(val plan: Plan): UseCase.Request
    data class Response(val plan: TypeRepo): UseCase.Response
}
