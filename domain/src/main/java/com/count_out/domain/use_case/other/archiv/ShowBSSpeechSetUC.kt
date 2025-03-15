package com.count_out.domain.use_case.other.archiv

import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ShowBSSpeechSetUC @Inject constructor(configuration: Configuration
): UseCase<ShowBSSpeechSetUC.Request, ShowBSSpeechSetUC.Response>(configuration)  {
    override fun executeData(input: Request): Flow<Response> =
        flow { emit( Response(!input.request) ) }
    data class Request(val request: Boolean) : UseCase.Request
    data class Response(val result: Boolean) : UseCase.Response
}