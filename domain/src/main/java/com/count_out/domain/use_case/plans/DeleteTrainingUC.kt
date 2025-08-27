package com.count_out.domain.use_case.plans

import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.Training
import com.count_out.domain.repository.plans.TrainingRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteTrainingUC @Inject constructor(configuration: Configuration, private val repo: TrainingRepo
): UseCase<DeleteTrainingUC.Request, DeleteTrainingUC.Response>(configuration)  {
    override fun method(request: Request): Flow<ResultUC<TypeRepo>> =
        repo.del(TypeRepo.PlanT( request.training))
    override fun response(typeRepo: TypeRepo): Response = Response(typeRepo)
    data class Request(val training: Training) : UseCase.Request
    data class Response(val trainings: TypeRepo) : UseCase.Response
}
//    override fun implementation_old(request: Request): Flow<Response> = repo.del(request.training).map { Response(it) }
//    override fun implementation(request: Request): Flow<ResultUC<Response>> =
//        repo.del(request.training).map { ResultUC.Success(Response(it))