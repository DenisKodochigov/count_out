package com.count_out.domain.use_case.plans.activity

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.repository.TypeRepo
import com.count_out.domain.repository.plans.ActivityRepo
import com.count_out.domain.use_case.UseCase
import com.count_out.domain.use_case.plans.GetTrainingsUC
import com.count_out.domain.use_case.plans.GetTrainingsUC.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DeleteActivityUC @Inject constructor(
    configuration: Configuration, private val repo: ActivityRepo
): UseCase<DeleteActivityUC.Request, DeleteActivityUC.Response>(configuration)  {

    override fun methodRepo(request: Request): Flow<ResultUC<TypeRepo>> =
        repo.del(TypeRepo.ActivityT(request.activity))
    override fun response(typeRepo: TypeRepo): Response = Response
    data class Request(val activity: Activity): UseCase.Request
    data object Response: UseCase.Response
}
//
//    override fun implementation(request: Request): Flow<ResultUC<Response>> =
//        repo.del(request.activity).map { ResultUC.Success(Response) }