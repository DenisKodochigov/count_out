package com.example.count_out.data.repository

import com.example.count_out.data.source.network.WeatherSource
import com.example.count_out.domain.repository.WeatherRepo
import com.example.count_out.entity.weather.Weather
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherRepoImpl @Inject constructor( private val source: WeatherSource): WeatherRepo {
    override fun get(latitude: Double, longitude: Double, timezone: String): Flow<Weather> =
        source.get(latitude, longitude, timezone)
}