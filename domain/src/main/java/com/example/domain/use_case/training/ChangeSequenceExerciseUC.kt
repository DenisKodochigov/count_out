package com.example.domain.use_case.training

import com.example.domain.entity.DataForChangeSequence
import com.example.domain.entity.Exercise
import com.example.domain.repository.trainings.ExerciseRepo
import com.example.domain.use_case.UseCase
import com.example.domain.use_case.UseCase.Configuration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChangeSequenceExerciseUC @Inject constructor(
    configuration: Configuration, private val repo: ExerciseRepo
): UseCase<ChangeSequenceExerciseUC.Request, ChangeSequenceExerciseUC.Response>(configuration)  {
    override fun executeData(input: Request): Flow<Response> =
        repo.changeSequenceExercise(input.item).map { Response(it) }
    data class Request(val item: DataForChangeSequence): UseCase.Request
    data class Response(val exercise: Exercise): UseCase.Response
}