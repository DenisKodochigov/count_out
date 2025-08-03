package com.count_out.data.models

import com.count_out.domain.entity.weather.WeatherRequest

data class WeatherRequestImplD (
    override val latitude: Double,
    override val longitude: Double,
    override val timeZone: String
): WeatherRequest