package com.count_out.domain.use_case.plans.exercise

import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.repository.plans.ExerciseRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateExerciseUC @Inject constructor(
    configuration: Configuration, private val repo: ExerciseRepo
): UseCase<UpdateExerciseUC.Request, UpdateExerciseUC.Response>(configuration)  {

    override fun method(request: Request): Flow<ResultDomain<Domain>> = repo.update(request.exercise)
    override fun response(result: Domain): Response = Response(result)
    data class Request(val exercise: Exercise): UseCase.Request
    data class Response(val training: Domain): UseCase.Response
}
//    override fun implementation(request: Request): Flow<ResultUC<Response>> =
//        repo.update(request.exercise).map { ResultUC.Success(Response(it)) }