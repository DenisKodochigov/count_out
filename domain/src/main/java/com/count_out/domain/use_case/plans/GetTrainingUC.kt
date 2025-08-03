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

class GetTrainingUC @Inject constructor(configuration: Configuration, private val repo: TrainingRepo
): UseCase<GetTrainingUC.Request, GetTrainingUC.Response>(configuration)  {

    override fun methodRepo(request: Request): Flow<ResultUC<TypeRepo>> =
        repo.get(TypeRepo.PlanT(request.training))
    override fun response(typeRepo: TypeRepo): Response = Response(typeRepo)
    data class Request(val training: Training) : UseCase.Request
    data class Response(val training: TypeRepo) : UseCase.Response
}
//    override fun implementation_old(request: Request): Flow<Response> {
//        return repo.get(request.training).map { Response(it) } }
//    override fun implementation(request: Request): Flow<ResultUC<Response>> =
//        repo.get(request.training).map { ResultUC.Success(Response(it)) }