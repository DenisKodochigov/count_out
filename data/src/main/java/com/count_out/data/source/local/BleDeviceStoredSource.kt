package com.count_out.data.source.local

import kotlinx.coroutines.flow.Flow

interface BleDeviceStoredSource {
    fun getBleAddress(): Flow<String>
    fun getBleName(): Flow<String>
    suspend fun saveBleName(settings: String)
    suspend fun saveBleAddress(settings: String)
}