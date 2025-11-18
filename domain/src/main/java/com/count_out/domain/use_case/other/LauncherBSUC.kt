package com.count_out.domain.use_case.other

import com.count_out.domain.core.LauncherBottomSheetCore
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LauncherBSUC @Inject constructor(
    private val configuration: Configuration, private val core: LauncherBottomSheetCore
): UseCase<LauncherBSUC.Request, LauncherBSUC.Response>(configuration)  {

    override fun method(request: Request): Flow<ResultDomain<Domain>> = core.get(request.launcher)
    override fun response(result: Domain): Response = Response(result)
    data class Request(val launcher: Domain) : UseCase.Request
    data class Response(val launcher: Domain) : UseCase.Response
}
