package com.count_out.domain.use_case.speech

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.SpeechKit
import com.count_out.domain.repository.TypeRepo
import com.count_out.domain.repository.plans.SpeechKitRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateSpeechKitUC @Inject constructor(
    configuration: Configuration, private val repo: SpeechKitRepo
): UseCase<UpdateSpeechKitUC.Request, UpdateSpeechKitUC.Response>(configuration)  {

    override fun methodRepo(request: Request): Flow<ResultUC<TypeRepo>> =
        repo.update(TypeRepo.SpeechKitT( request.speech))
    override fun response(typeRepo: TypeRepo): Response = Response(typeRepo)
    data class Request(val speech: SpeechKit): UseCase.Request
    data class Response(val speech: TypeRepo): UseCase.Response
}
//    override fun implementation(request: Request): Flow<ResultUC<Response>> =
//        repo.update(request.speech).map { converterR(it){ it1-> Response(it1) } }