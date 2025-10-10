package com.count_out.domain.use_case.other

import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.repository.CountOutServiceRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CountOutServiceUnBindUC @Inject constructor(configuration: Configuration, private val repo: CountOutServiceRepo
): UseCase<CountOutServiceUnBindUC.Request, CountOutServiceUnBindUC.Response>(configuration)  {

    override fun method(request: Request): Flow<ResultDomain<Domain>> = repo.unbind()
    override fun response(result: Domain): Response = Response(result)
    data object Request : UseCase.Request
    data class Response(val result: Domain) : UseCase.Response
}
//    override fun implementation(request: Request): Flow<ResultUC<Response>> =
//        repo.unbind().map { ResultUC.Success(Response(it)) }