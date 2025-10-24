package com.count_out.domain.use_case.other

import com.count_out.domain.core.CollapsingCore
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CollapsingUC @Inject constructor(
    private val configuration: Configuration, private val core: CollapsingCore
): UseCase<CollapsingUC.Request, CollapsingUC.Response>(configuration)  {

    override fun method(request: Request): Flow<ResultDomain<Domain>> =
        core.get(request.collaps)
    override fun response(result: Domain): Response = Response(result)
    data class Request(val collaps: Domain) : UseCase.Request
    data class Response(val collaps: Domain) : UseCase.Response
}
