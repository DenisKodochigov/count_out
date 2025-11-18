package com.count_out.domain.use_case.location

import com.count_out.domain.core.LocationCore
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StartLocationUC @Inject constructor(
    configuration: Configuration, private val core: LocationCore
): UseCase<StartLocationUC.Request, StartLocationUC.Response>(configuration)  {

    override fun method(request: Request): Flow<ResultDomain<Domain>> = core.getLocation()
    override fun response(result: Domain): Response = Response(result)
    data object Request: UseCase.Request
    data class Response(val result: Domain): UseCase.Response
}//
//    override fun implementation(request: Request): Flow<ResultUC<Response>> =
//        repo.startScanning().map { result->
//            converterR(result){ Response(it)} }