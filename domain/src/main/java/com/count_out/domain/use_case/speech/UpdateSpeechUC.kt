package com.count_out.domain.use_case.speech

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.Speech
import com.count_out.domain.repository.plans.SpeechRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UpdateSpeechUC @Inject constructor(configuration: Configuration,  private val repo: SpeechRepo
): UseCase<UpdateSpeechUC.Request, UpdateSpeechUC.Response>(configuration)  {
    override fun implementation(request: Request): Flow<ResultUC<Response>> =
        repo.update(request.speech).map {
            converterR(it){ speech-> Response(speech) } }
    data class Request(val speech: Speech): UseCase.Request
    data class Response(val speech: Speech?): UseCase.Response
}