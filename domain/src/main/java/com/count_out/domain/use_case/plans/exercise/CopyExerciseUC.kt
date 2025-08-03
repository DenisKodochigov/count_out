package com.count_out.domain.use_case.plans.exercise

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.repository.TypeRepo
import com.count_out.domain.repository.plans.ExerciseRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CopyExerciseUC @Inject constructor(
    configuration: Configuration, private val repo: ExerciseRepo
): UseCase<CopyExerciseUC.Request, CopyExerciseUC.Response>(configuration)  {

    override fun methodRepo(request: Request): Flow<ResultUC<TypeRepo>> =
        repo.copy(TypeRepo.ExerciseT(request.exercise))
    override fun response(typeRepo: TypeRepo): Response = Response(typeRepo)
    data class Request(val exercise: Exercise): UseCase.Request
    data class Response(val training: TypeRepo): UseCase.Response
}
//
//    override fun implementation(request: Request): Flow<ResultUC<Response>> =
//        repo.copy(request.exercise).map { ResultUC.Success(Response(it)) }