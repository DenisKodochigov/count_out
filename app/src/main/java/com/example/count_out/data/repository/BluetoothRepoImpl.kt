package com.example.count_out.data.repository

import com.example.count_out.data.source.framework.BleSource
import com.example.count_out.domain.repository.BluetoothRepo
import com.example.count_out.entity.bluetooth.DeviceUI
import com.example.count_out.entity.models.DeviceUIImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BluetoothRepoImpl @Inject constructor(private val bleSource: BleSource): BluetoothRepo {
    override fun startScanning(): Flow<Boolean> {
        bleSource.startScanning()
        return flow { emit(true) }
    }

    override fun stopScanning(): Flow<Boolean> {
        return flow { emit(true) }
    }

    override fun connectDevice(): Flow<DeviceUI> {
        return flow { emit(DeviceUIImpl()) }
    }

    override fun clearCache(): Flow<Boolean> {
        return flow { emit(true) }
    }

    override fun selectDeice(device: DeviceUI): Flow<Boolean> {
        return flow { emit(true) }
    }

    override fun getStateBle(): Flow<Boolean> {
        return flow { emit(true) }
    }
}