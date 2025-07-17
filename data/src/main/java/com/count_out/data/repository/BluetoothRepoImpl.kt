package com.count_out.data.repository

import com.count_out.data.entity.ConverterResult
import com.count_out.data.models.DeviceUIImpl
import com.count_out.data.source.framework.BleSource
import com.count_out.data.source.local.LastBleDeviceSource
import com.count_out.domain.entity.router.DeviceUI
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.BluetoothRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BluetoothRepoImpl @Inject constructor(
    private val converter: ConverterResult,
    private val bleSource: BleSource,
    private val savedDevice: LastBleDeviceSource
): BluetoothRepo, PrimeRepo() {
    override fun converter(): ConverterResult = converter
    override fun startScanning(): Flow<ResultUC<Boolean>> {
        bleSource.startScanning()
        return flow { emit(ResultUC.Success(data = true) )}
    }

    override fun stopScanning(): Flow<ResultUC<Boolean>>{
        return flow { emit(ResultUC.Success(data = true) )}
    }

    override fun connectDevice(): Flow<ResultUC<DeviceUI>>{
        return flow { emit(ResultUC.Success(data = DeviceUIImpl())) }
    }

    override fun lastDevice(): Flow<ResultUC<DeviceUI>>{
        return savedDevice.getDevice().concat{ bleSource.connectDevice(it) }
    }
    override fun clearCache(): Flow<ResultUC<Boolean>> {
        return flow { emit(ResultUC.Success(data = true) )}
    }

    override fun selectDeice(device: DeviceUI): Flow<ResultUC<Boolean>> {
        return flow { emit(ResultUC.Success(data = true) )}
    }

    override fun getStateBle(): Flow<ResultUC<Boolean>> {
        return flow { emit(ResultUC.Success(data = true) )}
    }
}