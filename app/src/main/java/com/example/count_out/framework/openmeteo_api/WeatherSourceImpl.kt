package com.example.count_out.framework.openmeteo_api

import com.example.count_out.data.source.network.WeatherSource
import com.example.count_out.entity.throwable.ThrowableSD
import com.example.count_out.entity.weather.Weather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WeatherSourceImpl @Inject constructor(private val weatherService: WeatherService): WeatherSource {

//    suspend fun getWeather(workout: WorkoutDB): ResponseAPI {
//
//        return CoroutineScope(Dispatchers.Default).async {
//            weatherService.getWeather( workout.latitude,workout.longitude, workout.timeZone) }.await()
//    }
    override fun get(latitude: Double, longitude: Double, timezone: String): Flow<Weather> =
        flow { emit ( weatherService.getWeather(latitude, longitude, timezone)) }
            .map { it.current.toWeatherSource() }
            .catch { throw ThrowableSD.WeatherTrow(it) }
}