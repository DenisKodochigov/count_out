package com.count_out.domain.repository

import com.count_out.domain.entity.router.DeviceUI
import com.count_out.domain.entity.throwable.ResultUC
import kotlinx.coroutines.flow.Flow

interface BluetoothRepo {
    fun startScanning(): Flow<ResultUC<Boolean>>
    fun stopScanning(): Flow<ResultUC<Boolean>>
    fun connectDevice(): Flow<ResultUC<DeviceUI>>
    fun lastDevice(): Flow<ResultUC<DeviceUI>>
    fun clearCache(): Flow<ResultUC<Boolean>>
    fun selectDeice(device: DeviceUI): Flow<ResultUC<Boolean>>
    fun getStateBle(): Flow<ResultUC<Boolean>>
}