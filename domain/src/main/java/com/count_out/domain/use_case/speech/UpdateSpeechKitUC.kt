package com.count_out.domain.use_case.speech

import com.count_out.domain.entity.workout.SpeechKit
import com.count_out.domain.repository.trainings.SpeechRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UpdateSpeechKitUC @Inject constructor(
    configuration: Configuration, private val repo: SpeechRepo
): UseCase<UpdateSpeechKitUC.Request, UpdateSpeechKitUC.Response>(configuration)  {
    override fun executeData(input: Request): Flow<Response> =
        repo.update(input.speech).map { Response(it) }
    data class Request(val speech: SpeechKit): UseCase.Request
    data class Response(val speech: SpeechKit): UseCase.Response
}