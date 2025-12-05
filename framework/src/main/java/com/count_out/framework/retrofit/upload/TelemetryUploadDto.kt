package com.count_out.framework.retrofit.upload

data class TelemetryUploadDto(
    val id: Long,
    val timestamp: Long,
    val lat: Double,
    val lon: Double,
    val heartRate: Int?
)
