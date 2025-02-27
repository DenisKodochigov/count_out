package com.count_out.data.repository

import com.count_out.data.source.framework.BleSource
import com.count_out.domain.entity.bluetooth.DeviceUI
import com.count_out.domain.repository.BluetoothRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BluetoothRepoImpl @Inject constructor(private val bleSource: BleSource): BluetoothRepo {
    override fun startScanning(): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override fun stopScanning(): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override fun connectDevice(): Flow<DeviceUI> {
        TODO("Not yet implemented")
    }

    override fun clearCache(): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override fun selectDeice(device: DeviceUI): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override fun getStateBle(): Flow<Boolean> {
        TODO("Not yet implemented")
    }
}