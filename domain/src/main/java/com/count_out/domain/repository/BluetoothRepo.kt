package com.count_out.domain.repository

import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import kotlinx.coroutines.flow.Flow

interface BluetoothRepo {
    fun startScanning(): Flow<ResultDomain<Domain>>
    fun stopScanning(): Flow<ResultDomain<Domain>>
    fun connectDevice(address: Domain): Flow<ResultDomain<Domain>>
//    fun lastDevice(): Flow<ResultDomain<Domain>>
    fun clearCache(): Flow<ResultDomain<Domain>>
    fun getStateBle(): Flow<ResultDomain<Domain>>
    fun getHeartRate(): Flow<ResultDomain<Domain>>
}