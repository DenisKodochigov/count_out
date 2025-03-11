package com.count_out.domain.use_case.exercise

import com.count_out.domain.entity.DataForChangeSequence
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.repository.trainings.ExerciseRepo
import com.count_out.domain.use_case.UseCase
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