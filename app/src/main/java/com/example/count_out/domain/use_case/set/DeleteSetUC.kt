package com.example.count_out.domain.use_case.set

import com.example.count_out.entity.workout.ActionWithSet
import com.example.count_out.entity.workout.Set
import com.example.count_out.domain.repository.trainings.SetRepo
import com.example.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DeleteSetUC @Inject constructor(
    configuration: Configuration, private val repo: SetRepo
): UseCase<DeleteSetUC.Request, DeleteSetUC.Response>(configuration)  {
    override fun executeData(input: Request): Flow<Response> =
        repo.del(input.item).map { Response(it) }
    data class Request(val item: ActionWithSet): UseCase.Request
    data class Response(val set: List<Set>): UseCase.Response
}