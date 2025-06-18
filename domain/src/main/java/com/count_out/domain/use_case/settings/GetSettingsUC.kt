package com.count_out.domain.use_case.settings

import com.count_out.domain.entity.Settings
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.trainings.SettingsRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSettingsUC @Inject constructor(
    configuration: Configuration, private val repo: SettingsRepo
): UseCase<GetSettingsUC.Request, GetSettingsUC.Response>(configuration)  {
//    override fun implementation_old(input: Request): Flow<Response> =
//        repo.getSettings().map { Response(it) }
    override fun implementation(request: Request): Flow<ResultUC<Response>> =
        repo.getSettings().map { ResultUC.Success(Response(it)) }

    data object Request: UseCase.Request
    data class Response(val setting: Settings): UseCase.Response
}