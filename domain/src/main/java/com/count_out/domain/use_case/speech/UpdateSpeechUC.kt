package com.count_out.domain.use_case.speech

import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.Speech
import com.count_out.domain.repository.plans.SpeechRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateSpeechUC @Inject constructor(configuration: Configuration,  private val repo: SpeechRepo
): UseCase<UpdateSpeechUC.Request, UpdateSpeechUC.Response>(configuration)  {

    override fun method(request: Request): Flow<ResultDomain<Domain>> =
        repo.update(request.speech)
    override fun response(result: Domain): Response = Response(result)

    data class Request(val speech: Speech): UseCase.Request
    data class Response(val speech: Domain): UseCase.Response
}