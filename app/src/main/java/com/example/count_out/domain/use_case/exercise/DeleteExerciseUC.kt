package com.example.count_out.domain.use_case.exercise

import com.example.count_out.entity.workout.Exercise
import com.example.count_out.domain.repository.trainings.ExerciseRepo
import com.example.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DeleteExerciseUC @Inject constructor(
    configuration: Configuration, private val repo: ExerciseRepo
): UseCase<DeleteExerciseUC.Request, DeleteExerciseUC.Response>(configuration)  {
    override fun executeData(input: Request): Flow<Response> =
        repo.del(input.exercise).map { Response(it) }
    data class Request(val exercise: Exercise): UseCase.Request
    data class Response(val exercise: List<Exercise>): UseCase.Response
}