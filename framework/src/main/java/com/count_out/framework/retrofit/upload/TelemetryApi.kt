package com.count_out.framework.retrofit.upload

import retrofit2.http.Body
import retrofit2.http.POST

interface TelemetryApi {
    @POST("telemetry/upload")
    suspend fun uploadTelemetry(
        @Body request: TelemetryUploadRequest
    ): UploadResponse
}