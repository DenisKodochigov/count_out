package com.count_out.domain.use_case.plans.exercise

import com.count_out.domain.core.ChangeSequenceCore
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChangeSequenceUC @Inject constructor(
    configuration: Configuration, private val core: ChangeSequenceCore
): UseCase<ChangeSequenceUC.Request, ChangeSequenceUC.Response>(configuration)  {

    override fun method(request: Request): Flow<ResultDomain<Domain>> = core.set(request.item)
    override fun response(result: Domain): Response = Response(result)
    data class Request(val item: Domain): UseCase.Request
    data class Response(val training: Domain): UseCase.Response
}