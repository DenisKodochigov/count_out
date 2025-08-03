package com.count_out.device.bluetooth

import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.models.throwable.TypeSource
import com.count_out.data.source.framework.BleSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BleSourceImpl @Inject constructor(): BleSource {
    override fun startScanning(): Flow<ResultSource<TypeSource>> {
        return flow { emit(ResultSource.Error(ThrowableDS.RequestFailed())) }
    }

    override fun stopScanning(): Flow<ResultSource<TypeSource>> {
        return flow { emit(ResultSource.Error(ThrowableDS.RequestFailed())) }
    }

    override fun connectDevice(addr: TypeSource): Flow<ResultSource<TypeSource>> {
        return flow { emit(ResultSource.Error(ThrowableDS.RequestFailed())) }
    }

    override fun clearCache(): Flow<ResultSource<TypeSource>> {
        return flow { emit(ResultSource.Error(ThrowableDS.RequestFailed())) }
    }

    override fun selectDeice(device: TypeSource): Flow<ResultSource<TypeSource>> {
        return flow { emit(ResultSource.Error(ThrowableDS.RequestFailed())) }
    }

    override fun getStateBle(): Flow<ResultSource<TypeSource>> {
        return flow { emit(ResultSource.Error(ThrowableDS.RequestFailed())) }
    }
}