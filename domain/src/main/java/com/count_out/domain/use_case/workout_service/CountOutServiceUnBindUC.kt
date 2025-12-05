package com.count_out.domain.use_case.workout_service

import com.count_out.domain.core.CountOutServiceCore
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CountOutServiceUnBindUC @Inject constructor(configuration: Configuration, private val core: CountOutServiceCore
): UseCase<CountOutServiceUnBindUC.Request, CountOutServiceUnBindUC.Response>(configuration)  {

    override fun method(request: Request): Flow<ResultDomain<Domain>> = core.unbind()
    override fun response(result: Domain): Response = Response(result)
    data object Request : UseCase.Request
    data class Response(val result: Domain) : UseCase.Response
}
