package com.example.count_out.framework.openmeteo_api


import com.example.count_out.framework.openmeteo_api.dto.ResponseDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WeatherService {
    //https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&current=temperature_2m,relative_humidity_2m,apparent_temperature,is_day,precipitation,rain,showers,snowfall,weather_code,cloud_cover,pressure_msl,surface_pressure,wind_speed_10m,wind_direction_10m,wind_gusts_10m&wind_speed_unit=ms&timezone=Europe%2FMoscow&forecast_days=1

    @Headers("Accept: application/json", "Content-type: application/json")
    @GET("/v1/forecast?&current=temperature_2m,relative_humidity_2m,apparent_temperature,is_day,precipitation,rain,showers,snowfall,weather_code,cloud_cover,pressure_msl,surface_pressure,wind_speed_10m,wind_direction_10m,wind_gusts_10m&wind_speed_unit=ms&forecast_days=1")
    suspend fun getWeather(
        @Query("latitude") latitude:Double,
        @Query("longitude") longitude: Double,
        @Query("timezone") timezone: String,): ResponseDto
}
