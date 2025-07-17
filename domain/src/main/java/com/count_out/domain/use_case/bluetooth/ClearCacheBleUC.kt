package com.count_out.domain.use_case.bluetooth

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.BluetoothRepo
import com.count_out.domain.use_case.UseCase
import com.count_out.domain.use_case.plans.activity.UpdateActivityUC.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ClearCacheBleUC @Inject constructor(
    configuration: Configuration, private val repo: BluetoothRepo
): UseCase<ClearCacheBleUC.Request, ClearCacheBleUC.Response>(configuration)  {

    override fun implementation(request: Request): Flow<ResultUC<Response>> =
        repo.clearCache().map { result->
            converterR(result){ Response(it)} }

    data object Request: UseCase.Request
    data class Response(val result: Boolean): UseCase.Response
}