package com.count_out.domain.use_case.plans

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.Training
import com.count_out.domain.repository.TypeRepo
import com.count_out.domain.repository.plans.TrainingRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateTrainingUC @Inject constructor(
    configuration: Configuration, private val repo: TrainingRepo
): UseCase<UpdateTrainingUC.Request, UpdateTrainingUC.Response>(configuration)  {

    override fun methodRepo(request: Request): Flow<ResultUC<TypeRepo>> = repo.gets()
    override fun response(typeRepo: TypeRepo): Response = Response(typeRepo)
    data class Request(val training: Training): UseCase.Request
    data class Response(val training: TypeRepo): UseCase.Response
}
//
//    fun implementation(request: Request): Flow<ResultUC<Response>> =
//        repo.update(request.training).map { ResultUC.Success(Response(it)) }