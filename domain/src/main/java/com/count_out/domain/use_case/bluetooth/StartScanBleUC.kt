package com.count_out.domain.use_case.bluetooth

import com.count_out.domain.repository.BluetoothRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StartScanBleUC @Inject constructor(
    configuration: Configuration, private val repo: BluetoothRepo
): UseCase<StartScanBleUC.Request, StartScanBleUC.Response>(configuration)  {
    override fun executeData(input: Request): Flow<Response> = repo.clearCache().map { Response(it) }
    data object Request: UseCase.Request
    data class Response(val result: Boolean): UseCase.Response
}