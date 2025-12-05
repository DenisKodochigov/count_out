package com.count_out.data.repository

import com.count_out.data.source.services.TelemetrySource
import com.count_out.domain.entity.ai.TelemetryPoint
import com.count_out.domain.repository.ai.TelemetryRepo
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class TelemetryRepoImpl @Inject constructor(private val source: TelemetrySource,
): TelemetryRepo {

    override fun insertBatch(points: List<TelemetryPoint>) {
        source.insertBatch(points)
    }

    override fun getPendingCountFlow(): Flow<Int> {
        return source.getPendingCountFlow()
    }

    override fun loadPending(limit: Int): List<TelemetryPoint> {
        return source.loadPending(limit)
    }

    override fun markSynced(ids: List<Long>) {
        source.markSynced(ids)
    }
}