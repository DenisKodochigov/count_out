package com.count_out.domain.use_case.bluetooth

import com.count_out.domain.entity.router.DeviceUI
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.BluetoothRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SelectDeviceBleUC @Inject constructor(
    configuration: Configuration, private val repo: BluetoothRepo
): UseCase<SelectDeviceBleUC.Request, SelectDeviceBleUC.Response>(configuration)  {
//    override fun implementation_old(request: Request): Flow<Response> =
//        repo.selectDeice(request.device).map { Response(it) }
    override fun implementation(request: Request): Flow<ResultUC<Response>> =
        repo.selectDeice(request.device).map { ResultUC.Success(Response(it)) }

    data class Request(val device: DeviceUI): UseCase.Request
    data class Response(val result: Boolean): UseCase.Response
}