package com.example.domain.use_case

import com.example.domain.entity.weather.Weather
import com.example.domain.repository.WeatherRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetWeatherUC @Inject constructor(configuration: Configuration, private val repo: WeatherRepo
): UseCase<GetWeatherUC.Request, GetWeatherUC.Response>(configuration)  {
    override fun executeData(input: Request): Flow<Response> = repo.get().map { Response(it) }
    data class Request(val id: Long) : UseCase.Request
    data class Response(val weather: Weather) : UseCase.Response
}