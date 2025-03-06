package com.example.count_out.domain.use_case.other

import com.example.count_out.domain.repository.CountOutServiceRepo
import com.example.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CountOutServiceBindUC @Inject constructor(configuration: Configuration, private val repo: CountOutServiceRepo
): UseCase<CountOutServiceBindUC.Request, CountOutServiceBindUC.Response>(configuration)  {
    override fun executeData(input: Request): Flow<Response> =
        repo.bind().map { Response(it) }
    data object Request : UseCase.Request
    data class Response(val result: Boolean) : UseCase.Response
}