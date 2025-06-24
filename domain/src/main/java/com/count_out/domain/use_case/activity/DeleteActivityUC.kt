package com.count_out.domain.use_case.activity

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.repository.trainings.ActivityRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DeleteActivityUC @Inject constructor(
    configuration: Configuration, private val repo: ActivityRepo
): UseCase<DeleteActivityUC.Request, DeleteActivityUC.Response>(configuration)  {

    override fun implementation(request: Request): Flow<ResultUC<Response>> =
        repo.del(request.activity).map { ResultUC.Success(Response) }

    data class Request(val activity: Activity): UseCase.Request
    data object Response: UseCase.Response
}