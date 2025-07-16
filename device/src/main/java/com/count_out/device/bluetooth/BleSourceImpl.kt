package com.count_out.device.bluetooth

import com.count_out.data.models.throwable.ResultDataSource
import com.count_out.data.source.framework.BleSource
import com.count_out.domain.entity.router.DeviceUI
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BleSourceImpl @Inject constructor(): BleSource {
    override fun startScanning(): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override fun stopScanning(): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override fun connectDevice(addr: String): Flow<ResultDataSource<DeviceUI>> {
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