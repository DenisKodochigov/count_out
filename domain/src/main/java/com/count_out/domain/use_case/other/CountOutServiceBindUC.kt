package com.count_out.domain.use_case.other

import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.CountOutServiceRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CountOutServiceBindUC @Inject constructor(configuration: Configuration, private val repo: CountOutServiceRepo
): UseCase<CountOutServiceBindUC.Request, CountOutServiceBindUC.Response>(configuration)  {

    override fun method(request: Request): Flow<ResultUC<TypeRepo>> = repo.bind()
    override fun response(typeRepo: TypeRepo): Response = Response(typeRepo)
    data object Request : UseCase.Request
    data class Response(val result: TypeRepo) : UseCase.Response
}
//    override fun implementation(request: Request): Flow<ResultUC<Response>> =
//        repo.bind().map { ResultUC.Success(Response(it)) }