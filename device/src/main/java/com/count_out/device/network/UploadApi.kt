package com.count_out.device.network

import retrofit2.http.Body
import retrofit2.http.POST

interface UploadApi {
    @POST("telemetry/upload")
    suspend fun uploadTelemetry(@Body data: List<TelemetryUploadDto>)
}