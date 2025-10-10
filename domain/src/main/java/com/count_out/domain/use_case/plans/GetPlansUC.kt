package com.count_out.domain.use_case.plans

import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.repository.plans.PlanRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlansUC @Inject constructor(configuration: Configuration, private val repo: PlanRepo
): UseCase<GetPlansUC.Request, GetPlansUC.Response>(configuration)  {

    override fun method(request: Request): Flow<ResultDomain<Domain>> = repo.gets()
    override fun response(result: Domain): Response = Response(result)
    data object Request : UseCase.Request
    data class Response(val plans: Domain): UseCase.Response
}