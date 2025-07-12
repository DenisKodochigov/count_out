package com.count_out.domain.use_case.speech

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.SpeechKit
import com.count_out.domain.repository.plans.SpeechKitRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UpdateSpeechKitUC @Inject constructor(
    configuration: Configuration, private val repo: SpeechKitRepo
): UseCase<UpdateSpeechKitUC.Request, UpdateSpeechKitUC.Response>(configuration)  {

    override fun implementation(request: Request): Flow<ResultUC<Response>> =
        repo.update(request.speech).map { converterR(it){ it1-> Response(it1) } }

    data class Request(val speech: SpeechKit): UseCase.Request
    data class Response(val speech: SpeechKit): UseCase.Response
}