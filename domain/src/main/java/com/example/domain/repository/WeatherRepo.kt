package com.example.domain.repository

import com.example.domain.entity.weather.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepo {
    fun get(latitude: Double, longitude: Double, timezone: String): Flow<Weather>
}