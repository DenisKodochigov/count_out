package com.count_out.domain.use_case.bluetooth

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.BluetoothRepo
import com.count_out.domain.repository.TypeRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StartScanBleUC @Inject constructor(
    configuration: Configuration, private val repo: BluetoothRepo
): UseCase<StartScanBleUC.Request, StartScanBleUC.Response>(configuration)  {

    override fun methodRepo(request: Request): Flow<ResultUC<TypeRepo>> = repo.startScanning()
    override fun response(typeRepo: TypeRepo): Response = Response(typeRepo)
    data object Request: UseCase.Request
    data class Response(val result: TypeRepo): UseCase.Response
}//
//    override fun implementation(request: Request): Flow<ResultUC<Response>> =
//        repo.startScanning().map { result->
//            converterR(result){ Response(it)} }