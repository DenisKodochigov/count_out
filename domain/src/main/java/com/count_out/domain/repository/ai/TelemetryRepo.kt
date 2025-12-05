package com.count_out.domain.repository.ai

import com.count_out.domain.entity.ai.TelemetryPoint
import kotlinx.coroutines.flow.Flow

interface TelemetryRepo {
    fun insertBatch(points: List<TelemetryPoint>)
    fun getPendingCountFlow(): Flow<Int>
    fun loadPending(limit: Int): List<TelemetryPoint>
    fun markSynced(ids: List<Long>)
}