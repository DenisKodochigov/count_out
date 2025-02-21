package com.count_out.data.repository

import com.count_out.data.source.network.WeatherSource
import com.count_out.domain.entity.weather.Weather
import com.count_out.domain.repository.WeatherRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherRepoImpl @Inject constructor( private val weatherSource: WeatherSource): WeatherRepo {
    override fun get(latitude: Double, longitude: Double, timezone: String): Flow<Weather> =
        weatherSource.get(latitude, longitude, timezone)
}