package com.count_out.data.source.local

import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.TypeSource
import com.count_out.domain.repository.TypeRepo
import kotlinx.coroutines.flow.Flow

interface LastBleDeviceSource {
    fun getDevice(): Flow<ResultSource<TypeSource>>
    suspend fun saveDevice(addr: TypeSource): Flow<ResultSource<TypeSource>>
}