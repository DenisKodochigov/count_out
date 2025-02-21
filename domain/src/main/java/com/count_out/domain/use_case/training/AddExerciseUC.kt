package com.count_out.domain.use_case.training

import com.count_out.domain.entity.Exercise
import com.count_out.domain.repository.trainings.ExerciseRepo
import com.count_out.domain.use_case.UseCase
import com.count_out.domain.use_case.UseCase.Configuration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AddExerciseUC @Inject constructor(
    configuration: Configuration, private val repo: ExerciseRepo
): UseCase<AddExerciseUC.Request, AddExerciseUC.Response>(configuration)  {
    override fun executeData(input: Request): Flow<Response> =
        repo.add(input.exercise).map { Response(it) }
    data class Request(val exercise: Exercise): UseCase.Request
    data class Response(val training: List<Exercise>): UseCase.Response
}