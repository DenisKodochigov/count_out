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
    fun toWeatherSource() = WeatherDb(
        time = this.time.toLong(),
        rain = this.rain,
        isDay = this.isDay,
        showers = this.showers,
        interval = this.interval,
        snowfall = this.snowfall,
        cloudCover = this.cloudCover,
        weatherCode = this.weatherCode,
        pressureMsl = this.pressureMsl,
        windSpeed10m = this.windSpeed10m,
        windGusts10m = this.windGusts10m,
        temperature2m = this.temperature2m,
        precipitation = this.precipitation,
        surfacePressure = this.surfacePressure,
        windDirection10m = this.windDirection10m,
        relativeHumidity2m = this.relativeHumidity2m,
        apparentTemperature = this.apparentTemperature,
    )
}
