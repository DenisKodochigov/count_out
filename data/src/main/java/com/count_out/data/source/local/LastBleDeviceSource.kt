package com.count_out.data.source.local

import com.count_out.data.models.throwable.ResultSource
import kotlinx.coroutines.flow.Flow

interface LastBleDeviceSource {
    fun getDevice(): Flow<ResultSource<String>>
    fun saveDevice(addr: String): Flow<ResultSource<Boolean>>
}