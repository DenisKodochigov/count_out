package com.count_out.data.source.framework


import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.TypeSource
import com.count_out.domain.entity.router.DeviceUI
import kotlinx.coroutines.flow.Flow

interface BleSource {
    fun startScanning(): Flow<ResultSource<TypeSource>>
    fun stopScanning(): Flow<ResultSource<TypeSource>>
    fun connectDevice(addr: TypeSource): Flow<ResultSource<TypeSource>>
    fun clearCache(): Flow<ResultSource<TypeSource>>
    fun selectDeice(device: TypeSource): Flow<ResultSource<TypeSource>>
    fun getStateBle(): Flow<ResultSource<TypeSource>>
}