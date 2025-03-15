package com.count_out.domain.use_case.other.archiv

import com.count_out.domain.entity.workout.Collapsing
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CollapsingListSetUC @Inject constructor(configuration: Configuration
): UseCase<CollapsingListSetUC.Request, CollapsingListSetUC.Response>(configuration)  {
    override fun executeData(input: Request): Flow<Response> =
        flow { emit( Response(executeCollapsing(input.request)))}// .map{ Response(it) }) }
    data class Request(val request: Collapsing) : UseCase.Request
    data class Response(val result: Collapsing) : UseCase.Response

    fun executeCollapsing(item: Collapsing): Collapsing{
        return item
    }
}