package com.count_out.domain.use_case.plans

import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.Plan
import com.count_out.domain.repository.plans.PlanRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeletePlanUC @Inject constructor(configuration: Configuration, private val repo: PlanRepo
): UseCase<DeletePlanUC.Request, DeletePlanUC.Response>(configuration)  {
    override fun method(request: Request): Flow<ResultDomain<Domain>> = repo.del(request.plan)
    override fun response(result: Domain): Response = Response(result)
    data class Request(val plan: Plan) : UseCase.Request
    data class Response(val plans: Domain) : UseCase.Response
}
//    override fun implementation_old(request: Request): Flow<Response> = repo.del(request.training).map { Response(it) }
//    override fun implementation(request: Request): Flow<ResultUC<Response>> =
//        repo.del(request.training).map { ResultUC.Success(Response(it))