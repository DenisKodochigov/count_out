package com.count_out.domain.use_case.settings

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.TypeRepo
import com.count_out.domain.repository.plans.SettingsRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSettingsUC @Inject constructor(
    configuration: Configuration, private val repo: SettingsRepo
): UseCase<GetSettingsUC.Request, GetSettingsUC.Response>(configuration)  {

    override fun methodRepo(request: Request): Flow<ResultUC<TypeRepo>> = repo.getSettings()
    override fun response(typeRepo: TypeRepo): Response = Response(typeRepo)
    data object Request: UseCase.Request
    data class Response(val setting: TypeRepo): UseCase.Response
}
//    override fun implementation(request: Request): Flow<ResultUC<Response>> =
//        repo.getSettings().map { ResultUC.Success(Response(it)) }