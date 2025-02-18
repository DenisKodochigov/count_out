package com.example.domain.use_case.training

import com.example.domain.entity.ActionWithActivity
import com.example.domain.entity.Exercise
import com.example.domain.repository.trainings.ExerciseRepo
import com.example.domain.use_case.UseCase
import com.example.domain.use_case.UseCase.Configuration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SelectActivityUC @Inject constructor(
    configuration: Configuration, private val repo: ExerciseRepo
): UseCase<SelectActivityUC.Request, SelectActivityUC.Response>(configuration)  {
    override fun executeData(input: Request): Flow<Response> =
        repo.selectActivity(input.activity).map { Response(it) }
    data class Request(val activity: ActionWithActivity): UseCase.Request
    data class Response(val exercise: Exercise): UseCase.Response
}