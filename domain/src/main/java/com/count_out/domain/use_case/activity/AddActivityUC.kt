package com.count_out.domain.use_case.activity

import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.repository.trainings.ActivityRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AddActivityUC @Inject constructor(
    configuration: Configuration, private val repo: ActivityRepo
): UseCase<AddActivityUC.Request, AddActivityUC.Response>(configuration)  {
    data class Request(val activity: Activity): UseCase.Request
    data class Response(val activity: Activity): UseCase.Response

    override fun executeData(input: Request): Flow<Response> =
        repo.add(input.activity).map { Response(it) }
}