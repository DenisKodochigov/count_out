package com.example.domain.use_case.training

import com.example.domain.entity.ActionWithSet
import com.example.domain.entity.Set
import com.example.domain.repository.trainings.SetRepo
import com.example.domain.use_case.UseCase
import com.example.domain.use_case.UseCase.Configuration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CopySetUC @Inject constructor(
    configuration: Configuration, private val repo: SetRepo
): UseCase<CopySetUC.Request, CopySetUC.Response>(configuration)  {
    override fun executeData(input: Request): Flow<Response> =
        repo.copy(input.item).map { Response(it) }
    data class Request(val item:ActionWithSet): UseCase.Request
    data class Response(val listSet: List<Set>): UseCase.Response
}