package com.count_out.framework.retrofit.weather.source

import com.count_out.data.entity.WeatherImpl
import com.count_out.data.source.network.WeatherSource
import com.count_out.data.throwable.ThrowableSD
import com.count_out.framework.retrofit.weather.WeatherService
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