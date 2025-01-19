package com.example.count_out.entity.weather

import com.example.domain.entity.weather.Weather
import com.example.domain.entity.weather.WeatherUnits

interface ResponseAPI {
    val latitude: Double
    val longitude: Double
    val generationTimeMS: Double
    val utcOffsetSeconds: Int
    val timezone: String
    val timezoneAbbreviation: String
    val elevation: Double
    val currentUnits: WeatherUnits?
    val current: Weather?
}