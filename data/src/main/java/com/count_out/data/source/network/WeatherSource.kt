package com.count_out.data.source.network

import com.count_out.domain.entity.weather.Weather
import kotlinx.coroutines.flow.Flow

interface  WeatherSource {
    fun get(latitude: Double, longitude: Double, timezone: String): Flow<Weather>
}