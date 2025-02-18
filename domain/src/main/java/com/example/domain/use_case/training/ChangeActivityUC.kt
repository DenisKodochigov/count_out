package com.example.domain.use_case.training

import com.example.domain.entity.Activity
import com.example.domain.repository.trainings.ActivityRepo
import com.example.domain.use_case.UseCase
import com.example.domain.use_case.UseCase.Configuration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChangeActivityUC @Inject constructor(
    configuration: Configuration, private val repo: ActivityRepo
): UseCase<ChangeActivityUC.Request, ChangeActivityUC.Response>(configuration)  {
    override fun executeData(input: Request): Flow<Response> =
        repo.update(input.activity).map { Response(it) }
    data class Request(val activity: Activity): UseCase.Request
    data class Response(val activity: Activity): UseCase.Response
}