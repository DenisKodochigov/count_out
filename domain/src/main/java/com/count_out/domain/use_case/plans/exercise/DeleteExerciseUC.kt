package com.count_out.domain.use_case.plans.exercise

import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.repository.plans.ExerciseRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteExerciseUC @Inject constructor(
    configuration: Configuration, private val repo: ExerciseRepo
): UseCase<DeleteExerciseUC.Request, DeleteExerciseUC.Response>(configuration)  {

    override fun method(request: Request): Flow<ResultDomain<Domain>> = repo.del(request.exercise)
    override fun response(result: Domain): Response = Response(result)
    data class Request(val exercise: Domain): UseCase.Request
    data class Response(val training: Domain): UseCase.Response
}
//    override fun implementation(request: Request): Flow<ResultUC<Response>> =
//        repo.del(request.exercise).map { ResultUC.Success(Response(it)) }