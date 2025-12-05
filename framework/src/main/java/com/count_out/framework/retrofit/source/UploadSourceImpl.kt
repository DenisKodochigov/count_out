package com.count_out.framework.retrofit.source

import com.count_out.framework.retrofit.upload.TelemetryApi
import com.count_out.framework.retrofit.upload.TelemetryUploadDto
import com.count_out.framework.retrofit.upload.TelemetryUploadRequest
import com.count_out.framework.room.db.telemetriy.TelemetryDao
import jakarta.inject.Inject

class UploadSourceImpl @Inject constructor(
    private val dao: TelemetryDao,
    private val api: TelemetryApi
) {

    suspend fun uploadNextBatch(batchSize: Int = 100): Boolean {
        val batch = dao.getBatch(batchSize)
        if (batch.isEmpty()) return false

        val dto = batch.map {
            TelemetryUploadDto(
                id = it.id,
                timestamp = it.timestamp,
                lat = it.lat,
                lon = it.lon,
                heartRate = it.heartRate
            )
        }

        val response = api.uploadTelemetry(TelemetryUploadRequest(items = dto))

        if (response.success) {
            dao.deleteByIds(response.uploadedIds)
            return true
        }

        return false
    }

    fun pendingCountFlow() = dao.countPendingFlow()
}