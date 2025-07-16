package com.count_out.data.repository

import com.count_out.data.entity.ConverterResult
import com.count_out.data.models.DeviceUIImpl
import com.count_out.data.models.throwable.ResultDataSource
import com.count_out.data.source.framework.BleSource
import com.count_out.data.source.local.LastBleDeviceSource
import com.count_out.domain.entity.router.DeviceUI
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.BluetoothRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BluetoothRepoImpl @Inject constructor(
    private val converter: ConverterResult,
    private val bleSource: BleSource,
    private val savedDevice: LastBleDeviceSource
): BluetoothRepo {
    override fun startScanning(): Flow<Boolean> {
        bleSource.startScanning()
        return flow { emit(true) }
    }

    override fun stopScanning(): Flow<Boolean> {
        return flow { emit(true) }
    }

    override fun connectDevice(): Flow<DeviceUI> {
        return flow { emit(DeviceUIImpl()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun lastDevice(): Flow<ResultUC<DeviceUI>> {
        return savedDevice.getDevice().flatMapConcat { resultDS->
            when(resultDS){
                is ResultDataSource.Error -> flow{emit(converter.execute(resultDS))}
                is ResultDataSource.Success -> {
                    bleSource.connectDevice(resultDS.data).map{converter.execute(it)}}
            }
        }
    }
    override fun clearCache(): Flow<Boolean> {
        return flow { emit(true) }
    }

    override fun selectDeice(device: DeviceUI): Flow<Boolean> {
        return flow { emit(true) }
    }

    override fun getStateBle(): Flow<Boolean> {
        return flow { emit(true) }
    }
}