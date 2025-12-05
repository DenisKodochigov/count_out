package com.count_out.framework.retrofit.upload

interface UploadApi {
    suspend fun uploadTelemetry(payload: List<Map<String, Any>>): Boolean
}