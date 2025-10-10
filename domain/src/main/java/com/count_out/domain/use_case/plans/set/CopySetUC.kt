package com.count_out.domain.use_case.plans.set

import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.Set
import com.count_out.domain.repository.plans.SetRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CopySetUC @Inject constructor(
    configuration: Configuration, private val repo: SetRepo
): UseCase<CopySetUC.Request, CopySetUC.Response>(configuration)  {

    override fun method(request: Request): Flow<ResultDomain<Domain>> = repo.copy(request.item)
    override fun response(result: Domain): Response = Response(result)
    data class Request(val item: Set): UseCase.Request
    data class Response(val training: Domain): UseCase.Response
}//
//    override fun implementation(request: Request): Flow<ResultUC<Response>> =
//        repo.copy(request.item).map { ResultUC.Success(Response(it)) }