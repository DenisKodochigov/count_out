package com.example.domain.use_case

import com.example.domain.entity.Activity
import com.example.domain.repository.training.ActivityRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetActivitiesUC @Inject constructor(configuration: Configuration, private val repo: ActivityRepo
): UseCase<GetActivitiesUC.Request, GetActivitiesUC.Response>(configuration) {
    override fun executeData(input: Request): Flow<Response> = repo.gets().map { Response(it) }
    data class Request(val id: Long) : UseCase.Request
    data class Response(val activity: List<Activity>): UseCase.Response
}