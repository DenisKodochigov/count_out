package com.count_out.domain.use_case.bluetooth

import com.count_out.domain.entity.router.DeviceUI
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.BluetoothRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LastBleDeviceUC @Inject constructor(
    configuration: Configuration, private val repo: BluetoothRepo
): UseCase<LastBleDeviceUC.Request, LastBleDeviceUC.Response>(configuration)  {

    override fun implementation(request: Request): Flow<ResultUC<Response>> {
        return repo.lastDevice().map { ResultUC.Success(Response(it)) }
    }
    data object Request: UseCase.Request
    data class Response(val result: DeviceUI): UseCase.Response
}