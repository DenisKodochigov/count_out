package com.example.count_out.domain.repository

import com.example.count_out.entity.bluetooth.DeviceUI
import kotlinx.coroutines.flow.Flow

interface BluetoothRepo {
    fun startScanning(): Flow<Boolean>
    fun stopScanning(): Flow<Boolean>
    fun connectDevice(): Flow<DeviceUI>
    fun clearCache(): Flow<Boolean>
    fun selectDeice(device: DeviceUI): Flow<Boolean>
    fun getStateBle(): Flow<Boolean>
}