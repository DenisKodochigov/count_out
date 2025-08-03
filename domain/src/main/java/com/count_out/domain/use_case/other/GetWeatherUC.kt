package com.count_out.domain.use_case.other

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.weather.WeatherRequest
import com.count_out.domain.repository.TypeRepo
import com.count_out.domain.repository.WeatherRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeatherUC @Inject constructor(configuration: Configuration, private val repo: WeatherRepo
): UseCase<GetWeatherUC.Request, GetWeatherUC.Response>(configuration)  {

    override fun methodRepo(request: Request): Flow<ResultUC<TypeRepo>> =
        repo.get(TypeRepo.WeatherRequestT(request.weatherRequest))
    override fun response(typeRepo: TypeRepo): Response = Response(typeRepo)
    data class Request(val weatherRequest: WeatherRequest) : UseCase.Request
    data class Response(val weather: TypeRepo) : UseCase.Response
}
//    override fun implementation(request: Request): Flow<ResultUC<Response>> =
//        repo.get(request.latitude, request.longitude, request.timezone).map { ResultUC.Success(Response(it)) }