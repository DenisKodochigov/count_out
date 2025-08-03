package com.count_out.domain.use_case.plans.set

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.Set
import com.count_out.domain.repository.TypeRepo
import com.count_out.domain.repository.plans.SetRepo
import com.count_out.domain.use_case.UseCase
import com.count_out.domain.use_case.plans.GetTrainingsUC
import com.count_out.domain.use_case.plans.GetTrainingsUC.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CopySetUC @Inject constructor(
    configuration: Configuration, private val repo: SetRepo
): UseCase<CopySetUC.Request, CopySetUC.Response>(configuration)  {

    override fun methodRepo(request: Request): Flow<ResultUC<TypeRepo>> =
        repo.copy(TypeRepo.SetT(request.item))
    override fun response(typeRepo: TypeRepo): Response = Response(typeRepo)
    data class Request(val item: Set): UseCase.Request
    data class Response(val training: TypeRepo): UseCase.Response
}//
//    override fun implementation(request: Request): Flow<ResultUC<Response>> =
//        repo.copy(request.item).map { ResultUC.Success(Response(it)) }