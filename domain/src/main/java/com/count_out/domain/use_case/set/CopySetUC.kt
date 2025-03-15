package com.count_out.domain.use_case.set

import com.count_out.domain.entity.workout.Set
import com.count_out.domain.entity.workout.Training
import com.count_out.domain.repository.trainings.SetRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CopySetUC @Inject constructor(
    configuration: Configuration, private val repo: SetRepo
): UseCase<CopySetUC.Request, CopySetUC.Response>(configuration)  {
    override fun executeData(input: Request): Flow<Response> =
        repo.copy(input.item).map { Response(it) }
    data class Request(val item: Set): UseCase.Request
    data class Response(val training: List<Set>): UseCase.Response
}