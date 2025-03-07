package com.example.count_out.data.source.network

import com.example.count_out.entity.weather.Weather
import kotlinx.coroutines.flow.Flow

interface  WeatherSource {
    fun get(latitude: Double, longitude: Double, timezone: String): Flow<Weather>
}