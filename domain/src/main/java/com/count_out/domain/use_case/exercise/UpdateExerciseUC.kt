package com.count_out.domain.use_case.exercise

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.repository.trainings.ExerciseRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UpdateExerciseUC @Inject constructor(
    configuration: Configuration, private val repo: ExerciseRepo
): UseCase<UpdateExerciseUC.Request, UpdateExerciseUC.Response>(configuration)  {
//    override fun implementation_old(request: Request): Flow<Response> =
//        repo.update(request.exercise).map { Response(it) }
    override fun implementation(request: Request): Flow<ResultUC<Response>> =
        repo.update(request.exercise).map { ResultUC.Success(Response(it)) }

    data class Request(val exercise: Exercise): UseCase.Request
    data class Response(val training: Exercise): UseCase.Response
}