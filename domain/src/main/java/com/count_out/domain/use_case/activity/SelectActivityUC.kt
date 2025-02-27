package com.count_out.domain.use_case.activity

import com.count_out.domain.entity.ActionWithActivity
import com.count_out.domain.entity.Exercise
import com.count_out.domain.repository.trainings.ExerciseRepo
import com.count_out.domain.use_case.UseCase
import com.count_out.domain.use_case.UseCase.Configuration
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