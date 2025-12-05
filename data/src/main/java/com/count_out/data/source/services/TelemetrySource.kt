package com.count_out.data.source.services

import com.count_out.domain.entity.ai.TelemetryPoint
import kotlinx.coroutines.flow.Flow

interface TelemetrySource {
    fun getPendingCount(): Int
//    fun triggerImmediateUpload()
//    fun setUseSimulator(enabled: Boolean)
    fun isUsingSimulatorFlow(): Flow<Boolean>
    fun insertBatch(points: List<TelemetryPoint>)
    fun getPendingCountFlow(): Flow<Int>
    fun loadPending(limit: Int): List<TelemetryPoint>
    fun markSynced(ids: List<Long>)
}