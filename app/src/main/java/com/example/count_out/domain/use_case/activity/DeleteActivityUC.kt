package com.example.count_out.domain.use_case.activity

import com.example.count_out.entity.workout.Activity
import com.example.count_out.domain.repository.trainings.ActivityRepo
import com.example.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DeleteActivityUC @Inject constructor(
    configuration: Configuration, private val repo: ActivityRepo
): UseCase<DeleteActivityUC.Request, DeleteActivityUC.Response>(configuration)  {
    override fun executeData(input: Request): Flow<Response> =
        repo.del(input.activity).map { Response }
    data class Request(val activity: Activity): UseCase.Request
    data object Response: UseCase.Response
}