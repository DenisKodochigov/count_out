package com.count_out.domain.use_case.plans

import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.plans.TrainingRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlanUC @Inject constructor(configuration: Configuration, private val repo: TrainingRepo
): UseCase<GetPlanUC.Request, GetPlanUC.Response>(configuration)  {

    override fun method(request: Request): Flow<ResultUC<TypeRepo>> =
        repo.get(TypeRepo.LongT(request.idPlan))
    override fun response(typeRepo: TypeRepo): Response = Response(typeRepo)
    data class Request(val idPlan: Long) : UseCase.Request
    data class Response(val plan: TypeRepo) : UseCase.Response
}
//    override fun implementation_old(request: Request): Flow<Response> {
//        return repo.get(request.training).map { Response(it) } }
//    override fun implementation(request: Request): Flow<ResultUC<Response>> =
//        repo.get(request.training).map { ResultUC.Success(Response(it)) }