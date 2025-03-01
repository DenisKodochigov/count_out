package com.count_out.data.repository

import com.count_out.data.models.DeviceUIImpl
import com.count_out.data.source.framework.BleSource
import com.count_out.domain.entity.bluetooth.DeviceUI
import com.count_out.domain.repository.BluetoothRepo
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