package com.count_out.domain.use_case.settings

import com.count_out.domain.entity.SettingRecord
import com.count_out.domain.repository.trainings.SettingsRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSettingUC @Inject constructor(
    configuration: Configuration, private val repo: SettingsRepo
): UseCase<GetSettingUC.Request, GetSettingUC.Response>(configuration)  {
    override fun executeData(input: Request): Flow<Response> =
        repo.getSetting(input.setting).map { Response(it) }
    data class Request(val setting: SettingRecord): UseCase.Request
    data class Response(val setting: SettingRecord): UseCase.Response
}