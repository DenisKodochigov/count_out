package com.count_out.domain.use_case.plans.set

import com.count_out.domain.core.plans.SetCore
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.Set
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChangeZoneUC @Inject constructor(
    configuration: Configuration, private val core: SetCore
): UseCase<ChangeZoneUC.Request, ChangeZoneUC.Response>(configuration)  {

    override fun method(request: Request): Flow<ResultDomain<Domain>> = core.changeZone(request.item)
    override fun response(result: Domain): Response = Response(result)
    data class Request(val item: Set): UseCase.Request
    data class Response(val item: Domain): UseCase.Response
}
