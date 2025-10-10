package com.count_out.domain.use_case.plans.activity

import com.count_out.domain.core.plans.ActivityCore
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateActivityUC @Inject constructor(
    configuration: Configuration, private val core: ActivityCore
): UseCase<UpdateActivityUC.Request, UpdateActivityUC.Response>(configuration)  {

    override fun method(request: Request): Flow<ResultDomain<Domain>> = core.update(request.activity)
    override fun response(result: Domain): Response = Response(result)
    data class Request(val activity: Domain): UseCase.Request
    data class Response(val activity: Domain): UseCase.Response
}
//
//    override fun implementation(request: Request): Flow<ResultUC<Response>> =
//        repo.update(request.activity).map{ result->
//            converterR(result){ Response(it)} }