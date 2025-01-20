package com.example.framework.retrofit.weather.source

import com.example.data.entity.WeatherImpl
import com.example.data.source.network.WeatherSource
import com.example.data.throwable.ThrowableSD
import com.example.framework.retrofit.weather.WeatherService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WeatherSourceImpl @Inject constructor(
    private val weatherService: WeatherService,
) : WeatherSource {
    override fun get(latitude: Double, longitude: Double, timezone: String): Flow<WeatherImpl> =
        flow { emit ( weatherService.getWeather(latitude, longitude, timezone)) }
            .map { it.current.toWeatherSource() }
            .catch { throw ThrowableSD.WeatherTrow(it) }
}