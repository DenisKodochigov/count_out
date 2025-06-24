package com.count_out.domain.use_case.other

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.weather.Weather
import com.count_out.domain.repository.WeatherRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetWeatherUC @Inject constructor(configuration: Configuration, private val repo: WeatherRepo
): UseCase<GetWeatherUC.Request, GetWeatherUC.Response>(configuration)  {

    override fun implementation(request: Request): Flow<ResultUC<Response>> =
        repo.get(request.latitude, request.longitude, request.timezone).map { ResultUC.Success(Response(it)) }

    data class Request(val latitude: Double, val longitude: Double, val timezone: String) : UseCase.Request
    data class Response(val weather: Weather) : UseCase.Response
}