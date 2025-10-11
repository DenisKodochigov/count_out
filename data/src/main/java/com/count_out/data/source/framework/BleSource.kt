package com.count_out.data.source.framework


import com.count_out.data.models.Data
import com.count_out.data.models.ResultData
import kotlinx.coroutines.flow.Flow

interface BleSource {
    fun startScanning(): Flow<ResultData<Data>>
    fun stopScanning(): Flow<ResultData<Data>>
    fun connectDevice(adr: Data): Flow<ResultData<Data>>
    fun clearCache(): Flow<ResultData<Data>>
    fun getStateBle(): Flow<ResultData<Data>>
    fun getHeartRate(): Flow<ResultData<Data>>
}