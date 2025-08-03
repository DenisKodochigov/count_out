package com.count_out.domain.use_case.plans

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.Training
import com.count_out.domain.repository.TypeRepo
import com.count_out.domain.repository.plans.TrainingRepo
import com.count_out.domain.use_case.UseCase
import com.count_out.domain.use_case.plans.GetTrainingsUC.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CopyTrainingUC @Inject constructor(configuration: Configuration, private val repo: TrainingRepo
): UseCase<CopyTrainingUC.Request, CopyTrainingUC.Response>(configuration)  {

    override fun methodRepo(request: Request): Flow<ResultUC<TypeRepo>> =
        repo.copy(TypeRepo.PlanT( request.training))
    override fun response(typeRepo: TypeRepo): Response = Response(typeRepo)
    data class Request(val training: Training) : UseCase.Request
    data class Response(val trainings: TypeRepo) : UseCase.Response
}
//    override fun implementation_old(request: Request): Flow<Response> = repo.copy(request.training).map { Response(it) }
//    fun implementation(request: Request): Flow<ResultUC<Response>> =
//        repo.copy(request.training).map { ResultUC.Success(Response(it)) }