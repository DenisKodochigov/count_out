package com.count_out.domain.use_case.activity

import com.count_out.domain.entity.Activity
import com.count_out.domain.repository.trainings.ActivityRepo
import com.count_out.domain.use_case.UseCase
import com.count_out.domain.use_case.UseCase.Configuration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetsActivityUC @Inject constructor(
    configuration: Configuration, private val repo: ActivityRepo
): UseCase<GetsActivityUC.Request, GetsActivityUC.Response>(configuration)  {
    override fun executeData(input: Request): Flow<Response> =
        repo.gets().map { Response(it) }
    data object Request: UseCase.Request
    data class Response(val activity: List<Activity>): UseCase.Response
}