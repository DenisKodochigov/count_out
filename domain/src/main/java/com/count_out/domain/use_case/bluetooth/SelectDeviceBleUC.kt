package com.count_out.domain.use_case.bluetooth

import com.count_out.domain.entity.bluetooth.DeviceUI
import com.count_out.domain.repository.BluetoothRepo
import com.count_out.domain.use_case.UseCase
import com.count_out.domain.use_case.UseCase.Configuration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SelectDeviceBleUC @Inject constructor(
    configuration: Configuration, private val repo: BluetoothRepo
): UseCase<SelectDeviceBleUC.Request, SelectDeviceBleUC.Response>(configuration)  {
    override fun executeData(input: Request): Flow<Response> =
        repo.selectDeice(input.device).map { Response(it) }
    data class Request(val device: DeviceUI): UseCase.Request
    data class Response(val result: Boolean): UseCase.Response
}