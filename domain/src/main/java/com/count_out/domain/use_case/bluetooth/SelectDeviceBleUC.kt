package com.count_out.domain.use_case.bluetooth

import com.count_out.domain.entity.router.DeviceUI
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.BluetoothRepo
import com.count_out.domain.repository.TypeRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SelectDeviceBleUC @Inject constructor(
    configuration: Configuration, private val repo: BluetoothRepo
): UseCase<SelectDeviceBleUC.Request, SelectDeviceBleUC.Response>(configuration)  {


    override fun methodRepo(request: Request): Flow<ResultUC<TypeRepo>> =
        repo.selectDeice(TypeRepo.DeviceUIT(request.device))
    override fun response(typeRepo: TypeRepo): Response = Response(typeRepo)
    data class Request(val device: DeviceUI): UseCase.Request
    data class Response(val result: TypeRepo): UseCase.Response
}//
//    override fun implementation(request: Request): Flow<ResultUC<Response>> =
//        repo.selectDeice(request.device).map { result->
//            converterR(result){ Response(it)} }