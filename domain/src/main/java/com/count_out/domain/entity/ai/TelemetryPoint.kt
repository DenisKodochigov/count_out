package com.count_out.domain.entity.ai

data class TelemetryPoint(
    val timestamp: Long,
    val lat: Double?,
    val lon: Double?,
    val heartRate: Int?
)