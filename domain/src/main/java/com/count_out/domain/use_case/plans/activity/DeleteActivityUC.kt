package com.count_out.domain.use_case.plans.activity

import com.count_out.domain.core.plans.ActivityCore
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteActivityUC @Inject constructor(
    configuration: Configuration, private val core: ActivityCore
): UseCase<DeleteActivityUC.Request, DeleteActivityUC.Response>(configuration)  {

    override fun method(request: Request): Flow<ResultDomain<Domain>> = core.del(request.activity)
    override fun response(result: Domain): Response = Response
    data class Request(val activity: Activity): UseCase.Request
    data object Response: UseCase.Response
}
//
//    override fun implementation(request: Request): Flow<ResultUC<Response>> =
//        repo.del(request.activity).map { ResultUC.Success(Response) }