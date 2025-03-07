package com.example.count_out.framework.openmeteo_api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseDto(
    @Json(name = "latitude")  val latitude: Double,
    @Json(name = "longitude")  val longitude: Double,
    @Json(name = "generationtime_ms")  val generationTimeMS: Double,
    @Json(name = "utc_offset_seconds")  val utcOffsetSeconds: Int,
    @Json(name = "timezone")  val timezone: String,
    @Json(name = "timezone_abbreviation")  val timezoneAbbreviation: String,
    @Json(name = "elevation")  val elevation: Double,
    @Json(name = "current_units")  val currentUnits: WeatherUnitsDto,
    @Json(name = "current")  val current: WeatherResponse,
)