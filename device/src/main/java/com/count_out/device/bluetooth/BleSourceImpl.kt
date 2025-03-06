package com.count_out.device.bluetooth

import com.count_out.data.source.framework.BleSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BleSourceImpl @Inject constructor(): BleSource {
    override fun startScanning(): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override fun stopScanning(): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override fun connectDevice(): Flow<com.count_out.domain.entity.router.DeviceUI> {
        TODO("Not yet implemented")
    }

    override fun clearCache(): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override fun selectDeice(device: com.count_out.domain.entity.router.DeviceUI): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override fun getStateBle(): Flow<Boolean> {
        TODO("Not yet implemented")
    }
}