package com.count_out.domain.use_case.plans

import com.count_out.domain.core.SelectingCore
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SelectingUC @Inject constructor(
    private val configuration: Configuration, private val core: SelectingCore
): UseCase<SelectingUC.Request, SelectingUC.Response>(configuration)  {

    override fun method(request: Request): Flow<ResultDomain<Domain>> = core.get(request.item)
    override fun response(result: Domain): Response = Response(result)
    data class Request(val item: Domain) : UseCase.Request
    data class Response(val item: Domain): UseCase.Response
}
