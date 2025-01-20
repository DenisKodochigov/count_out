package com.example.data.repository

import com.example.data.source.network.WeatherSource
import com.example.domain.entity.weather.Weather
import com.example.domain.repository.WeatherRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherRepoImpl @Inject constructor( private val weatherSource: WeatherSource): WeatherRepo {
    override fun get(latitude: Double, longitude: Double, timezone: String): Flow<Weather> =
        weatherSource.get(latitude, longitude, timezone)
}