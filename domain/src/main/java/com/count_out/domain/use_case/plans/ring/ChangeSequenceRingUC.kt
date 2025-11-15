package com.count_out.domain.use_case.plans.ring

import com.count_out.domain.core.plans.RingCore
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChangeSequenceRingUC @Inject constructor(
    configuration: Configuration, private val core: RingCore
): UseCase<ChangeSequenceRingUC.Request, ChangeSequenceRingUC.Response>(configuration)  {

    override fun method(request: Request): Flow<ResultDomain<Domain>> =
        core.changeSequenceExercise(request.item)
    override fun response(result: Domain): Response = Response(result)
    data class Request(val item: Domain): UseCase.Request
    data class Response(val training: Domain): UseCase.Response
}