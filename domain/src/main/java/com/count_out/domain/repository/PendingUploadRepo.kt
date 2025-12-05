package com.count_out.domain.repository

import kotlinx.coroutines.flow.Flow

interface PendingUploadRepo {
    suspend fun getPendingCount(): Int
    fun triggerImmediateUpload()
    suspend fun setUseSimulator(enabled: Boolean)
    fun isUsingSimulatorFlow(): Flow<Boolean>
}