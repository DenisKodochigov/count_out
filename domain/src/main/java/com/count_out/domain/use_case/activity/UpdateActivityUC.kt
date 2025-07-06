package com.count_out.domain.use_case.activity

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.repository.plans.ActivityRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UpdateActivityUC @Inject constructor(
    configuration: Configuration, private val repo: ActivityRepo
): UseCase<UpdateActivityUC.Request, UpdateActivityUC.Response>(configuration)  {

    override fun implementation(request: Request): Flow<ResultUC<Response>> =
        repo.update(request.activity).map { ResultUC.Success(Response(it)) }

    data class Request(val activity: Activity): UseCase.Request
    data class Response(val activity: Activity): UseCase.Response
}