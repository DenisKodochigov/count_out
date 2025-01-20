package com.example.framework.retrofit.weather.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseOpenMeteo (
//    @Json(name = "latitude")  val latitude: Double = 0.0,
//    @Json(name = "longitude")  val longitude: Double = 0.0,
//    @Json(name = "generationtime_ms")  val generationTimeMS: Double = 0.0,
//    @Json(name = "utc_offset_seconds")  val utcOffsetSeconds: Int = 0,
//    @Json(name = "timezone")  val timezone: String = "",
//    @Json(name = "timezone_abbreviation")  val timezoneAbbreviation: String = "",
//    @Json(name = "elevation")  val elevation: Double = 0.0,
//    @Json(name = "current_units")  val currentUnits: WeatherUnitsOpenMeteo? = null,
//    @Json(name = "current")  val current: WeatherResponse? = null,
    @Json(name = "latitude")  val latitude: Double,
    @Json(name = "longitude")  val longitude: Double,
    @Json(name = "generationtime_ms")  val generationTimeMS: Double,
    @Json(name = "utc_offset_seconds")  val utcOffsetSeconds: Int,
    @Json(name = "timezone")  val timezone: String,
    @Json(name = "timezone_abbreviation")  val timezoneAbbreviation: String,
    @Json(name = "elevation")  val elevation: Double,
    @Json(name = "current_units")  val currentUnits: WeatherUnitsOpenMeteo,
    @Json(name = "current")  val current: WeatherResponse,
)