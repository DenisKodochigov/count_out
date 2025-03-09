//package com.count_out.app.data.openmeteo_api.dto
//
//import com.count_out.app.entity.weather.Weather
//import com.squareup.moshi.Json
//import com.squareup.moshi.JsonClass
//
//@JsonClass(generateAdapter = true)
//data class WeatherDTO(
//    @Json(name = "time") override val time: String  = "",
//    @Json(name = "interval") override val interval: Int = 0,
//    @Json(name = "temperature_2m") override val temperature2m: Double = 0.0,
//    @Json(name = "relative_humidity_2m") override val relativeHumidity2m: Int = 0,
//    @Json(name = "apparent_temperature") override val apparentTemperature: Double = 0.0,
//    @Json(name = "is_day") override val isDay: Int = 0,
//    @Json(name = "precipitation") override val precipitation: Double = 0.0,
//    @Json(name = "rain") override val rain: Double = 0.0,
//    @Json(name = "showers") override val showers: Double = 0.0,
//    @Json(name = "snowfall") override val snowfall: Double = 0.0,
//    @Json(name = "weather_code") override val weatherCode: Int = 0,
//    @Json(name = "cloud_cover") override val cloudCover: Int = 0,
//    @Json(name = "pressure_msl") override val pressureMsl: Double = 0.0,
//    @Json(name = "surface_pressure") override val surfacePressure: Double = 0.0,
//    @Json(name = "wind_speed_10m") override val windSpeed10m: Double = 0.0,
//    @Json(name = "wind_direction_10m") override val windDirection10m: Int = 0,
//    @Json(name = "wind_gusts_10m") override val windGusts10m: Double = 0.0,
//) : Weather
