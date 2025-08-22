package com.count_out.domain.use_case.other

import com.count_out.domain.core.CollapsingCore
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.entity.workout.Collapsing
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.entity.workout.Round
import com.count_out.domain.entity.workout.Set
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CollapsingUC @Inject constructor(
    private val configuration: Configuration, private val core: CollapsingCore
): UseCase<CollapsingUC.Request, CollapsingUC.Response>(configuration)  {

    override fun method(request: Request): Flow<ResultUC<TypeRepo>> =
        core.get(TypeRepo.CollapsingT(item = request.collaps))
    override fun response(typeRepo: TypeRepo): Response = Response(typeRepo)
    data class Request(val collaps: Collapsing) : UseCase.Request
    data class Response(val collaps: TypeRepo) : UseCase.Response
}
//    override fun implementation(request: Request): Flow<ResultUC<Response>> =
//        flow { emit( ResultUC.Success(Response(executeCollapsing(request.collaps)))) }