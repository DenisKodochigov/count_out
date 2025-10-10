package com.count_out.framework.retrofit.weather.entity

import com.count_out.data.models.WeatherDb
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherResponse(
    @param:Json(name = "time")  val time: String  = "0",
    @param:Json(name = "interval")  val interval: Int = 0,
    @param:Json(name = "temperature_2m")  val temperature2m: Double = 0.0,
    @param:Json(name = "relative_humidity_2m")  val relativeHumidity2m: Int = 0,
    @param:Json(name = "apparent_temperature")  val apparentTemperature: Double = 0.0,
    @param:Json(name = "is_day")  val isDay: Int = 0,
    @param:Json(name = "precipitation")  val precipitation: Double = 0.0,
    @param:Json(name = "rain")  val rain: Double = 0.0,
    @param:Json(name = "showers")  val showers: Double = 0.0,
    @param:Json(name = "snowfall")  val snowfall: Double = 0.0,
    @param:Json(name = "weather_code")  val weatherCode: Int = 0,
    @param:Json(name = "cloud_cover")  val cloudCover: Int = 0,
    @param:Json(name = "pressure_msl")  val pressureMsl: Double = 0.0,
    @param:Json(name = "surface_pressure")  val surfacePressure: Double = 0.0,
    @param:Json(name = "wind_speed_10m")  val windSpeed10m: Double = 0.0,
    @param:Json(name = "wind_direction_10m")  val windDirection10m: Int = 0,
    @param:Json(name = "wind_gusts_10m")  val windGusts10m: Double = 0.0,
){
    fun toWeatherSource() = object: WeatherDb() {
        override val time: Long = this@WeatherResponse.time.toLong()
        override val interval: Int = this@WeatherResponse.interval
        override val temperature2m: Double = this@WeatherResponse.temperature2m
        override val relativeHumidity2m: Int = this@WeatherResponse.relativeHumidity2m
        override val apparentTemperature: Double = this@WeatherResponse.apparentTemperature
        override val isDay: Int = this@WeatherResponse.isDay
        override val precipitation: Double = this@WeatherResponse.precipitation
        override val rain: Double = this@WeatherResponse.rain
        override val showers: Double = this@WeatherResponse.showers
        override val snowfall: Double = this@WeatherResponse.snowfall
        override val weatherCode: Int = this@WeatherResponse.weatherCode
        override val cloudCover: Int = this@WeatherResponse.cloudCover
        override val pressureMsl: Double = this@WeatherResponse.pressureMsl
        override val surfacePressure: Double = this@WeatherResponse.surfacePressure
        override val windSpeed10m: Double = this@WeatherResponse.windSpeed10m
        override val windDirection10m: Int = this@WeatherResponse.windDirection10m
        override val windGusts10m: Double = this@WeatherResponse.windGusts10m
    }
}
