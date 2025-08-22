package com.count_out.domain.repository

import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultUC
import kotlinx.coroutines.flow.Flow

interface BluetoothRepo {
    fun startScanning(): Flow<ResultUC<TypeRepo>>
    fun stopScanning(): Flow<ResultUC<TypeRepo>>
    fun connectDevice(address: TypeRepo): Flow<ResultUC<TypeRepo>>
    fun lastDevice(): Flow<ResultUC<TypeRepo>>
    fun clearCache(): Flow<ResultUC<TypeRepo>>
//    fun selectDeice(device: TypeRepo): Flow<ResultUC<TypeRepo>>
    fun getStateBle(): Flow<ResultUC<TypeRepo>>
    fun getHeartRate(): Flow<ResultUC<TypeRepo>>
}