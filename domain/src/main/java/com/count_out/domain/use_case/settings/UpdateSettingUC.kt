package com.count_out.domain.use_case.settings

import com.count_out.domain.core.plans.SettingsCore
import com.count_out.domain.entity.Settings
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateSettingUC @Inject constructor(
    configuration: Configuration, private val repo: SettingsCore
): UseCase<UpdateSettingUC.Request, UpdateSettingUC.Response>(configuration)  {

    override fun method(request: Request): Flow<ResultDomain<Domain>> =
        repo.saveSetting(request.setting)
    override fun response(result: Domain): Response = Response(result)
    data class Request(val setting: Settings): UseCase.Request
    data class Response(val setting: Domain): UseCase.Response
}
//    fun implementation(request: Request): Flow<ResultUC<Response>> =
//        repo.getSettings().map { ResultUC.Success(Response(it)) }