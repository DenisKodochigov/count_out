package com.count_out.device.bluetooth

import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.TypeSource
import com.count_out.data.source.framework.BleSource
import com.count_out.domain.entity.router.DeviceUI
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BleSourceImpl @Inject constructor(): BleSource {
    override fun startScanning(): Flow<ResultSource<TypeSource>> {
        TODO("Not yet implemented")
    }

    override fun stopScanning(): Flow<ResultSource<TypeSource>> {
        TODO("Not yet implemented")
    }

    override fun connectDevice(addr: TypeSource): Flow<ResultSource<TypeSource>> {
        TODO("Not yet implemented")
    }

    override fun clearCache(): Flow<ResultSource<TypeSource>> {
        TODO("Not yet implemented")
    }

    override fun selectDeice(device: TypeSource): Flow<ResultSource<TypeSource>> {
        TODO("Not yet implemented")
    }

    override fun getStateBle(): Flow<ResultSource<TypeSource>> {
        TODO("Not yet implemented")
    }
}