package com.count_out.domain.use_case.set

import com.count_out.domain.entity.workout.Set
import com.count_out.domain.repository.trainings.SetRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UpdateSetUC @Inject constructor(
    configuration: Configuration, private val repo: SetRepo
): UseCase<UpdateSetUC.Request, UpdateSetUC.Response>(configuration)  {
    override fun executeData(input: Request): Flow<Response> =
        repo.update(input.item).map { Response(it) }
    data class Request(val item: Set): UseCase.Request
    data class Response(val training: Set): UseCase.Response
}