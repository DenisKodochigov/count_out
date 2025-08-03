package com.count_out.domain.use_case.plans

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.Training
import com.count_out.domain.repository.TypeRepo
import com.count_out.domain.repository.plans.TrainingRepo
import com.count_out.domain.use_case.UseCase
import com.count_out.domain.use_case.speech.UpdateSpeechUC
import com.count_out.domain.use_case.speech.UpdateSpeechUC.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTrainingsUC @Inject constructor(configuration: Configuration, private val repo: TrainingRepo
): UseCase<GetTrainingsUC.Request, GetTrainingsUC.Response>(configuration)  {

    override fun methodRepo(request: Request): Flow<ResultUC<TypeRepo>> = repo.gets()
    override fun response(typeRepo: TypeRepo): Response = Response(typeRepo)
    data object Request : UseCase.Request
    data class Response(val trainings: TypeRepo): UseCase.Response
}