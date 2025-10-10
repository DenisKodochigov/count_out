package com.count_out.domain.use_case.plans

import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.repository.LastPlanRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveLastUsePlanUC @Inject constructor(configuration: Configuration, private val repo: LastPlanRepo
): UseCase<SaveLastUsePlanUC.Request, SaveLastUsePlanUC.Response>(configuration)  {
    override fun method(request: Request): Flow<ResultDomain<Domain>> =
        repo.saveLastUsedPlan(request.idTraining)
    override fun response(result: Domain): Response = Response(result)
    data class Request(val idTraining: Domain) : UseCase.Request
    data class Response(val result: Domain) : UseCase.Response
}
//
//    fun implementation(request: Request): Flow<ResultUC<Response>> =
//        repo.saveLastUsedPlan(request.idTraining).map {
//            converterR(it){ it1-> Response(it1)}}