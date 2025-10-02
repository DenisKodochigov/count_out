package com.count_out.domain.use_case.plans

import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.supportive.NameId
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.plans.PlanRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateNamePlanUC @Inject constructor(
    configuration: Configuration, private val repo: PlanRepo
): UseCase<UpdateNamePlanUC.Request, UpdateNamePlanUC.Response>(configuration)  {

    override fun method(request: Request): Flow<ResultUC<TypeRepo>> =
        repo.update(TypeRepo.NamIdT(request.nameID))
    override fun response(typeRepo: TypeRepo): Response = Response(typeRepo)
    data class Request(val nameID: NameId): UseCase.Request
    data class Response(val result: TypeRepo): UseCase.Response
}