package com.example.data.source.network

import com.example.domain.entity.weather.Weather
import kotlinx.coroutines.flow.Flow

interface  WeatherSource {
    fun get(): Flow<Weather>
}