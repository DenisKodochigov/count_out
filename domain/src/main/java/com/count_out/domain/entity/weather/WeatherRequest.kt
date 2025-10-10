package com.count_out.domain.entity.weather

import com.count_out.domain.entity.workout.Domain

interface WeatherRequest : Domain {
    val latitude: Double
    val longitude: Double
    val timeZone: String
}