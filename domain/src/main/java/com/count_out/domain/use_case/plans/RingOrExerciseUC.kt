package com.count_out.domain.use_case.plans

import com.count_out.domain.core.RingOrExerciseCore
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RingOrExerciseUC @Inject constructor(configuration: Configuration, private val core: RingOrExerciseCore
): UseCase<RingOrExerciseUC.Request, RingOrExerciseUC.Response>(configuration)  {

    override fun method(request: Request): Flow<ResultDomain<Domain>> =
        core.setRingOrExercise(request.ring)
    override fun response(result: Domain): Response = Response
    data class Request(val ring: Ring) : UseCase.Request
    data object Response : UseCase.Response
}
//    override fun implementation_old(request: Request): Flow<Response> = repo.copy(request.training).map { Response(it) }
//    fun implementation(request: Request): Flow<ResultUC<Response>> =
//        repo.copy(request.training).map { ResultUC.Success(Response(it)) }