package com.count_out.domain.use_case.settings

//class GetSettingUC @Inject constructor(
//    configuration: Configuration, private val repo: SettingsRepo
//): UseCase<GetSettingUC.Request, GetSettingUC.Response>(configuration)  {
//    override fun executeData(input: Request): Flow<Response> =
//        repo.getSetting(input.setting).map { Response(it) }
//    data class Request(val setting: Setting): UseCase.Request
//    data class Response(val setting: Settings): UseCase.Response
//}