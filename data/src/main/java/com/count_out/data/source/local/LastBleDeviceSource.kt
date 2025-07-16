package com.count_out.data.source.local

import com.count_out.data.models.throwable.ResultDataSource
import kotlinx.coroutines.flow.Flow

interface LastBleDeviceSource {
    fun getDevice(): Flow<ResultDataSource<String>>
    fun saveDevice(addr: String): Flow<ResultDataSource<Boolean>>
}