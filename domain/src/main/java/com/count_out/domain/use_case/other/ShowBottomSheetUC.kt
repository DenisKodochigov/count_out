package com.count_out.domain.use_case.other

import com.count_out.domain.core.ShowBSCore
import com.count_out.domain.core.plans.ActivityCore
import com.count_out.domain.entity.enums.RoundType
import com.count_out.domain.entity.router.DeviceBle
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.entity.workout.Round
import com.count_out.domain.entity.workout.Set
import com.count_out.domain.entity.workout.ShowBottomSheet
import com.count_out.domain.entity.workout.Training
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ShowBottomSheetUC @Inject constructor(
    private val configuration: Configuration, private val core: ShowBSCore
): UseCase<ShowBottomSheetUC.Request, ShowBottomSheetUC.Response>(configuration)  {

    override fun method(request: Request): Flow<ResultUC<TypeRepo>> =
        core.get(TypeRepo.ShowBottomSheetT(item = request.show))
    override fun response(typeRepo: TypeRepo): Response = Response(typeRepo)

    data class Request(val show: ShowBottomSheet) : UseCase.Request
    data class Response(val show: TypeRepo) : UseCase.Response


}
