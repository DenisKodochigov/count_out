package com.count_out.domain.use_case.plans

import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.repository.plans.PlanRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlanUC @Inject constructor(configuration: Configuration, private val repo: PlanRepo
): UseCase<GetPlanUC.Request, GetPlanUC.Response>(configuration)  {

    override fun method(request: Request): Flow<ResultDomain<Domain>> = repo.get(request.idPlan)
    override fun response(result: Domain): Response = Response(result)
    data class Request(val idPlan: Domain) : UseCase.Request
    data class Response(val plan: Domain) : UseCase.Response
}
//    override fun implementation_old(request: Request): Flow<Response> {
//        return repo.get(request.training).map { Response(it) } }
//    override fun implementation(request: Request): Flow<ResultUC<Response>> =
//        repo.get(request.training).map { ResultUC.Success(Response(it)) }