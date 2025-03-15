package com.count_out.domain.use_case.exercise

import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Training
import com.count_out.domain.repository.trainings.ExerciseRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UpdateExerciseUC @Inject constructor(
    configuration: Configuration, private val repo: ExerciseRepo
): UseCase<UpdateExerciseUC.Request, UpdateExerciseUC.Response>(configuration)  {
    override fun executeData(input: Request): Flow<Response> =
        repo.update(input.exercise).map { Response(it) }
    data class Request(val exercise: Exercise): UseCase.Request
    data class Response(val training: Exercise): UseCase.Response
}