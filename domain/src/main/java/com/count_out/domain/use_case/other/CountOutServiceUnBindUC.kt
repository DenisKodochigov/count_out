package com.count_out.domain.use_case.other

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.CountOutServiceRepo
import com.count_out.domain.repository.TypeRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CountOutServiceUnBindUC @Inject constructor(configuration: Configuration, private val repo: CountOutServiceRepo
): UseCase<CountOutServiceUnBindUC.Request, CountOutServiceUnBindUC.Response>(configuration)  {

    override fun methodRepo(request: Request): Flow<ResultUC<TypeRepo>> = repo.unbind()
    override fun response(typeRepo: TypeRepo): Response = Response(typeRepo)
    data object Request : UseCase.Request
    data class Response(val result: TypeRepo) : UseCase.Response
}
//    override fun implementation(request: Request): Flow<ResultUC<Response>> =
//        repo.unbind().map { ResultUC.Success(Response(it)) }