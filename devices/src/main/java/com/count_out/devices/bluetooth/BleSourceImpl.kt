package com.count_out.devices.bluetooth

import com.count_out.data.source.framework.BleSource
import com.count_out.domain.entity.bluetooth.DeviceUI
import kotlinx.coroutines.flow.Flow

class BleSourceImpl: BleSource {
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