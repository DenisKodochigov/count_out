package com.count_out.domain.repository

import com.count_out.domain.entity.router.DeviceUI
import kotlinx.coroutines.flow.Flow

interface BluetoothRepo {
    fun startScanning(): Flow<Boolean>
    fun stopScanning(): Flow<Boolean>
    fun connectDevice(): Flow<DeviceUI>
    fun clearCache(): Flow<Boolean>
    fun selectDeice(device: DeviceUI): Flow<Boolean>
    fun getStateBle(): Flow<Boolean>
}