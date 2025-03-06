package com.count_out.domain.entity.weather

interface WeatherUnits {
    val time: String
    val interval: String
    val temperature2m: String
    val relativeHumidity2m: String
    val apparentTemperature: String
    val isDay: String
    val precipitation: String
    val rain: String
    val showers: String
    val snowfall: String
    val weatherCode: String
    val cloudCover: String
    val pressureMsl: String
    val surfacePressure: String
    val windSpeed10m: String
    val windDirection10m: String
    val windGusts10m: String
}