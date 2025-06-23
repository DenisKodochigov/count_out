package com.count_out.domain.use_case.other

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.CountOutServiceRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CountOutServiceUnBindUC @Inject constructor(configuration: Configuration, private val repo: CountOutServiceRepo
): UseCase<CountOutServiceUnBindUC.Request, CountOutServiceUnBindUC.Response>(configuration)  {
//    override fun implementation_old(input: Request): Flow<Response> = repo.unbind().map { Response(it) }
    override fun implementation(request: Request): Flow<ResultUC<Response>> =
        repo.unbind().map { ResultUC.Success(Response(it)) }

    data object Request : UseCase.Request
    data class Response(val result: Boolean) : UseCase.Response
}