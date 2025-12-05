package com.count_out.device.bluetooth.ai

import kotlinx.coroutines.flow.Flow

interface HeartRateProvider {
    val heartRateFlow: Flow<Int?>             // последние значение пульса (nullable)
    val rrIntervalFlow: Flow<List<Int>>       // RR-интервалы в миллисекундах (может приходить пустой список)
    val connectionStateFlow: Flow<BleConnectionState>
    suspend fun startScanAndConnect(timeoutMs: Long = 10_000L)
    suspend fun connectTo(address: String)
    suspend fun disconnect()
    fun isConnected(): Boolean
}