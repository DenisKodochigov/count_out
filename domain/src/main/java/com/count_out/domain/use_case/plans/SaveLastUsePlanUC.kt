package com.count_out.domain.use_case.plans

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.LastPlanRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SaveLastUsePlanUC @Inject constructor(configuration: Configuration, private val repo: LastPlanRepo
): UseCase<SaveLastUsePlanUC.Request, SaveLastUsePlanUC.Response>(configuration)  {

    override fun implementation(request: Request): Flow<ResultUC<Response>> =
        repo.saveLastUsedPlan(request.idTraining).map {
            converterR(it){ it1-> Response(it1)}}

    data class Request(val idTraining: Long) : UseCase.Request
    data class Response(val result: Boolean) : UseCase.Response
}