package com.count_out.framework.retrofit.weather.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherUnitsOpenMeteo(
    @param:Json(name = "time") val time: String = "",
    @param:Json(name = "interval") val interval: String = "",
    @param:Json(name = "temperature_2m") val temperature2m: String = "",
    @param:Json(name = "relative_humidity_2m") val relativeHumidity2m: String = "",
    @param:Json(name = "apparent_temperature") val apparentTemperature: String = "",
    @param:Json(name = "is_day") val isDay: String = "",
    @param:Json(name = "precipitation") val precipitation: String = "",
    @param:Json(name = "rain") val rain: String = "",
    @param:Json(name = "showers") val showers: String = "",
    @param:Json(name = "snowfall") val snowfall: String = "",
    @param:Json(name = "weather_code") val weatherCode: String = "",
    @param:Json(name = "cloud_cover") val cloudCover: String = "",
    @param:Json(name = "pressure_msl") val pressureMsl: String = "",
    @param:Json(name = "surface_pressure") val surfacePressure: String = "",
    @param:Json(name = "wind_speed_10m") val windSpeed10m: String = "",
    @param:Json(name = "wind_direction_10m") val windDirection10m: String = "",
    @param:Json(name = "wind_gusts_10m") val windGusts10m: String = "",
)
