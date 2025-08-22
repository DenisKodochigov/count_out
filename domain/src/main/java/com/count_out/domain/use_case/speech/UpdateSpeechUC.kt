package com.count_out.domain.use_case.speech

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.Speech
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.repository.plans.SpeechRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateSpeechUC @Inject constructor(configuration: Configuration,  private val repo: SpeechRepo
): UseCase<UpdateSpeechUC.Request, UpdateSpeechUC.Response>(configuration)  {

    override fun method(request: Request): Flow<ResultUC<TypeRepo>> =
        repo.update(TypeRepo.SpeechT( request.speech))
    override fun response(typeRepo: TypeRepo): Response = Response(typeRepo)

    data class Request(val speech: Speech): UseCase.Request
    data class Response(val speech: TypeRepo): UseCase.Response
}