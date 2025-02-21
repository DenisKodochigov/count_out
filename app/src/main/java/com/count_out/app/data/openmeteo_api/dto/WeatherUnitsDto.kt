//package com.count_out.app.data.openmeteo_api.dto
//
//import com.count_out.app.entity.weather.WeatherUnits
//import com.squareup.moshi.Json
//import com.squareup.moshi.JsonClass
//
//@JsonClass(generateAdapter = true)
//data class WeatherUnitsDto(
//    @Json(name = "time") override val time: String = "",
//    @Json(name = "interval") override val interval: String = "",
//    @Json(name = "temperature_2m") override val temperature2m: String = "",
//    @Json(name = "relative_humidity_2m") override val relativeHumidity2m: String = "",
//    @Json(name = "apparent_temperature") override val apparentTemperature: String = "",
//    @Json(name = "is_day") override val isDay: String = "",
//    @Json(name = "precipitation") override val precipitation: String = "",
//    @Json(name = "rain") override val rain: String = "",
//    @Json(name = "showers") override val showers: String = "",
//    @Json(name = "snowfall") override val snowfall: String = "",
//    @Json(name = "weather_code") override val weatherCode: String = "",
//    @Json(name = "cloud_cover") override val cloudCover: String = "",
//    @Json(name = "pressure_msl") override val pressureMsl: String = "",
//    @Json(name = "surface_pressure") override val surfacePressure: String = "",
//    @Json(name = "wind_speed_10m") override val windSpeed10m: String = "",
//    @Json(name = "wind_direction_10m") override val windDirection10m: String = "",
//    @Json(name = "wind_gusts_10m") override val windGusts10m: String = "",
//): WeatherUnits
