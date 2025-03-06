package com.example.count_out.domain.use_case.settings

import com.example.count_out.entity.settings.Settings
import com.count_out.domain.repository.trainings.SettingsRepo
import com.example.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSettingsUC @Inject constructor(
    configuration: Configuration, private val repo: SettingsRepo
): UseCase<GetSettingsUC.Request, GetSettingsUC.Response>(configuration)  {
    override fun executeData(input: Request): Flow<Response> =
        repo.getSettings().map { Response(it) }
    data object Request: UseCase.Request
    data class Response(val setting: Settings): UseCase.Response
}