package com.count_out.domain.use_case.plans.ring

import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.repository.plans.RingRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CopyRingUC @Inject constructor(configuration: Configuration, private val repo: RingRepo
): UseCase<CopyRingUC.Request, CopyRingUC.Response>(configuration)  {

    override fun method(request: Request): Flow<ResultDomain<Domain>> = repo.insert(request.ring)
    override fun response(result: Domain): Response = Response(result)
    data class Request(val ring: Ring): UseCase.Request
    data class Response(val plan: Domain): UseCase.Response
}
