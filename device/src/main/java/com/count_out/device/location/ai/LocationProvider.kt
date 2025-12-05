package com.count_out.device.location.ai

import kotlinx.coroutines.flow.Flow

interface LocationProvider {
    val locationFlow: Flow<LocationData>
    suspend fun start()
    suspend fun stop()
}