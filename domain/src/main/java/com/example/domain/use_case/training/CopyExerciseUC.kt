package com.example.domain.use_case.training

import com.example.domain.entity.Exercise
import com.example.domain.repository.trainings.ExerciseRepo
import com.example.domain.use_case.UseCase
import com.example.domain.use_case.UseCase.Configuration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CopyExerciseUC @Inject constructor(
    configuration: Configuration, private val repo: ExerciseRepo
): UseCase<CopyExerciseUC.Request, CopyExerciseUC.Response>(configuration)  {
    override fun executeData(input: Request): Flow<Response> =
        repo.copy(input.exercise).map { Response(it) }
    data class Request(val exercise: Exercise): UseCase.Request
    data class Response(val exercises: List<Exercise>): UseCase.Response
}