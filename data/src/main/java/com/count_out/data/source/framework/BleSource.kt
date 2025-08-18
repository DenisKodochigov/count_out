package com.count_out.data.source.framework


import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.TypeSource
import kotlinx.coroutines.flow.Flow

interface BleSource {
    fun startScanning(): Flow<ResultSource<TypeSource>>
    fun stopScanning(): Flow<ResultSource<TypeSource>>
    fun connectDevice(adr: TypeSource): Flow<ResultSource<TypeSource>>
    fun clearCache(): Flow<ResultSource<TypeSource>>
    fun getStateBle(): Flow<ResultSource<TypeSource>>
    fun getHeartRate(): Flow<ResultSource<TypeSource>>
}