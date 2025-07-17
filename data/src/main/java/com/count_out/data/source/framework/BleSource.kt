package com.count_out.data.source.framework


import com.count_out.data.models.throwable.ResultSource
import com.count_out.domain.entity.router.DeviceUI
import kotlinx.coroutines.flow.Flow

interface BleSource {
    fun startScanning(): Flow<Boolean>
    fun stopScanning(): Flow<Boolean>
    fun connectDevice(addr: String): Flow<ResultSource<DeviceUI>>
    fun clearCache(): Flow<Boolean>
    fun selectDeice(device: DeviceUI): Flow<Boolean>
    fun getStateBle(): Flow<Boolean>
}