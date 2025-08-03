package com.count_out.data.repository

import com.count_out.data.source.framework.BleSource
import com.count_out.data.source.local.LastBleDeviceSource
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.BluetoothRepo
import com.count_out.domain.repository.TypeRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BluetoothRepoImpl @Inject constructor(
//    private val converter: ConverterResult,
    private val bleSource: BleSource,
    private val savedDevice: LastBleDeviceSource
): BluetoothRepo, PrimeRepo() {
//    override fun converter(): ConverterResult = converter
    override fun startScanning(): Flow<ResultUC<TypeRepo>> {
        return bleSource.startScanning().convertor() }

    override fun stopScanning(): Flow<ResultUC<TypeRepo>>{
        return bleSource.stopScanning().convertor() }

    override fun connectDevice(addr: TypeRepo): Flow<ResultUC<TypeRepo>>{
        return bleSource.connectDevice(toTypeSource(addr)).convertor() }

    override fun lastDevice(): Flow<ResultUC<TypeRepo>>{
        return savedDevice.getDevice().concat1{ bleSource.connectDevice(it) }
    }
    override fun clearCache(): Flow<ResultUC<TypeRepo>> {
        return flow { emit(ResultUC.Success(TypeRepo.BooleanT(item = true)) )} }

    override fun selectDeice(device: TypeRepo): Flow<ResultUC<TypeRepo>> {
        return flow { emit(ResultUC.Success(TypeRepo.BooleanT(item = true)) )} }

    override fun getStateBle(): Flow<ResultUC<TypeRepo>> {
        return flow { emit(ResultUC.Success(TypeRepo.BooleanT(item = true)) )} }
}