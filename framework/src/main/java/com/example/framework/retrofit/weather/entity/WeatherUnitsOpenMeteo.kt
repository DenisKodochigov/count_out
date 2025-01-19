package com.example.framework.retrofit.weather.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherUnitsOpenMeteo(
    @Json(name = "time") val time: String = "",
    @Json(name = "interval") val interval: String = "",
    @Json(name = "temperature_2m") val temperature2m: String = "",
    @Json(name = "relative_humidity_2m") val relativeHumidity2m: String = "",
    @Json(name = "apparent_temperature") val apparentTemperature: String = "",
    @Json(name = "is_day") val isDay: String = "",
    @Json(name = "precipitation") val precipitation: String = "",
    @Json(name = "rain") val rain: String = "",
    @Json(name = "showers") val showers: String = "",
    @Json(name = "snowfall") val snowfall: String = "",
    @Json(name = "weather_code") val weatherCode: String = "",
    @Json(name = "cloud_cover") val cloudCover: String = "",
    @Json(name = "pressure_msl") val pressureMsl: String = "",
    @Json(name = "surface_pressure") val surfacePressure: String = "",
    @Json(name = "wind_speed_10m") val windSpeed10m: String = "",
    @Json(name = "wind_direction_10m") val windDirection10m: String = "",
    @Json(name = "wind_gusts_10m") val windGusts10m: String = "",
)
