package com.count_out.domain.use_case.plans

import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.Plan
import com.count_out.domain.repository.plans.PlanRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdatesPlanUC @Inject constructor(
    configuration: Configuration, private val repo: PlanRepo
): UseCase<UpdatesPlanUC.Request, UpdatesPlanUC.Response>(configuration)  {

    override fun method(request: Request): Flow<ResultDomain<Domain>> =
        repo.update(request.plan)
    override fun response(result: Domain): Response = Response(result)
    data class Request(val plan: Plan): UseCase.Request
    data class Response(val plan: Domain): UseCase.Response
}
