package com.count_out.domain.use_case.plans.activity

import com.count_out.domain.core.plans.ActivityCore
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddActivityUC @Inject constructor(
    configuration: Configuration, private val core: ActivityCore
): UseCase<AddActivityUC.Request, AddActivityUC.Response>(configuration)  {
    override fun method(request: Request): Flow<ResultUC<TypeRepo>> =
        core.copy(TypeRepo.ActivityT(request.activity))
    override fun response(typeRepo: TypeRepo): Response = Response(typeRepo)
    data class Request(val activity: Activity): UseCase.Request
    data class Response(val activity: TypeRepo): UseCase.Response
}
