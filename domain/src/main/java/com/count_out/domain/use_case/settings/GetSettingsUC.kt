package com.count_out.domain.use_case.settings

import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.repository.plans.SettingsRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSettingsUC @Inject constructor(
    configuration: Configuration, private val repo: SettingsRepo
): UseCase<GetSettingsUC.Request, GetSettingsUC.Response>(configuration)  {

    override fun method(request: Request): Flow<ResultDomain<Domain>> = repo.getSettings()
    override fun response(result: Domain): Response = Response(result)
    data object Request: UseCase.Request
    data class Response(val setting: Domain): UseCase.Response
}
//    override fun implementation(request: Request): Flow<ResultUC<Response>> =
//        repo.getSettings().map { ResultUC.Success(Response(it)) }