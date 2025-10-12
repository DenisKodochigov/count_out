package com.count_out.domain.use_case.bluetooth

import com.count_out.domain.core.BluetoothCore
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetConnectionStateUC @Inject constructor(
    configuration: Configuration, private val core: BluetoothCore
): UseCase<GetConnectionStateUC.Request, GetConnectionStateUC.Response>(configuration)  {

    override fun method(request: Request): Flow<ResultDomain<Domain>> = core.getStateBle()
    override fun response(result: Domain): Response = Response(result)
    data object Request: UseCase.Request
    data class Response(val result: Domain): UseCase.Response
}
