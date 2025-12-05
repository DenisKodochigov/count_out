package com.count_out.domain.use_case.workout_service

import com.count_out.domain.core.ExecuteWorkOutCore
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StartWorkoutUC @Inject constructor(configuration: Configuration, private val core: ExecuteWorkOutCore
): UseCase<StartWorkoutUC.Request, StartWorkoutUC.Response>(configuration)  {

    override fun method(request: Request): Flow<ResultDomain<Domain>> { return core.start() }
    override fun response(result: Domain): Response = Response
    data object Request: UseCase.Request
    data object Response: UseCase.Response
}
