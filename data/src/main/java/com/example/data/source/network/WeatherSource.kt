package com.example.data.source.network

import com.example.domain.entity.weather.Weather
import kotlinx.coroutines.flow.Flow

interface  WeatherSource {
    fun get(latitude: Double, longitude: Double, timezone: String): Flow<Weather>
}