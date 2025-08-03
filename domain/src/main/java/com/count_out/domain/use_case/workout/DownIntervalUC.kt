package com.count_out.domain.use_case.workout

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.ExecuteWorkOutRepo
import com.count_out.domain.repository.TypeRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DownIntervalUC @Inject constructor(configuration: Configuration, private val repo: ExecuteWorkOutRepo
): UseCase<DownIntervalUC.Request, DownIntervalUC.Response>(configuration)  {

    override fun methodRepo(request: Request): Flow<ResultUC<TypeRepo>> {
        repo.downInterval()
        return flow { emit(ResultUC.Success(TypeRepo.BooleanT(item = true))) }
    }
    override fun response(typeRepo: TypeRepo): Response = Response
    data object Request: UseCase.Request
    data object Response: UseCase.Response
}
//
//    override fun implementation(request: Request): Flow<ResultUC<Response>> {
//        repo.downInterval()
//        return flow { emit(ResultUC.Success(Response)) }
//    }