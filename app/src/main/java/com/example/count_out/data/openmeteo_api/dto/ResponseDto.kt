package com.example.count_out.data.openmeteo_api.dto

import com.example.count_out.entity.weather.ResponseAPI
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseDto(
    @Json(name = "latitude") override val latitude: Double = 0.0,
    @Json(name = "longitude") override val longitude: Double = 0.0,
    @Json(name = "generationtime_ms") override val generationTimeMS: Double = 0.0,
    @Json(name = "utc_offset_seconds") override val utcOffsetSeconds: Int = 0,
    @Json(name = "timezone") override val timezone: String = "",
    @Json(name = "timezone_abbreviation") override val timezoneAbbreviation: String = "",
    @Json(name = "elevation") override val elevation: Double = 0.0,
    @Json(name = "current_units") override val currentUnits: WeatherUnitsDto? = null,
    @Json(name = "current") override val current: WeatherDTO? = null,
): ResponseAPI