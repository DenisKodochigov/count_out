package com.example.count_out.data.source.framework

import com.example.count_out.entity.bluetooth.DeviceUI
import kotlinx.coroutines.flow.Flow

interface BleSource {
    fun startScanning(): Flow<Boolean>
    fun stopScanning(): Flow<Boolean>
    fun connectDevice(): Flow<DeviceUI>
    fun clearCache(): Flow<Boolean>
    fun selectDeice(device: DeviceUI): Flow<Boolean>
    fun getStateBle(): Flow<Boolean>
}