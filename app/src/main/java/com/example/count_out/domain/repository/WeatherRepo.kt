package com.example.count_out.domain.repository

import com.example.count_out.entity.weather.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepo {
    fun get(latitude: Double, longitude: Double, timezone: String): Flow<Weather>
}