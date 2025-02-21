package com.count_out.domain.repository

import com.count_out.domain.entity.weather.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepo {
    fun get(latitude: Double, longitude: Double, timezone: String): Flow<Weather>
}