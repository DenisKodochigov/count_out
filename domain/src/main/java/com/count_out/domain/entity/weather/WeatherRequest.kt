package com.count_out.domain.entity.weather

interface WeatherRequest {
    val latitude: Double
    val longitude: Double
    val timeZone: String
}