package com.count_out.domain.use_case.bluetooth

import com.count_out.domain.core.BluetoothCore
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StopScanBleUC @Inject constructor(
    configuration: Configuration, private val core: BluetoothCore
): UseCase<StopScanBleUC.Request, StopScanBleUC.Response>(configuration)  {

    override fun method(request: Request): Flow<ResultUC<TypeRepo>> = core.stopScanning()
    override fun response(typeRepo: TypeRepo): Response = Response(typeRepo)
    data object Request: UseCase.Request
    data class Response(val result: TypeRepo): UseCase.Response
}//
//    override fun implementation(request: Request): Flow<ResultUC<Response>> =
//        repo.stopScanning().map { result->
//            converterR(result){ Response(it)} }