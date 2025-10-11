package com.count_out.device.bluetooth

import com.count_out.data.models.Data
import com.count_out.data.models.ResultData
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.models.types_data.BooleanDb
import com.count_out.data.models.types_data.LongDb
import com.count_out.data.models.types_data.MapDb
import com.count_out.data.source.framework.BleSource
import com.count_out.device.bluetooth.models.ResultBle
import com.count_out.domain.entity.router.DeviceBle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BleSourceImpl @Inject constructor(private val ble: Bluetooth): BleSource {
    override fun startScanning(): Flow<ResultData<Data>> {
        val mapDevice: MutableMap<String,DeviceBle> = mutableMapOf()
        return ble.startScanning().map { devUI->
            when(devUI){
                is ResultBle.Error -> ResultData.Error(throwable = ThrowableDS.extract(t = devUI.throwable))
                is ResultBle.Nothing -> ResultData.Success(LongDb(0L))
                is ResultBle.Device -> {
                    mapDevice.put(devUI.device.address, devUI.device)
                    ResultData.Success(MapDb(mapDevice ))
                }
                else -> ResultData.Error(throwable = ThrowableDS.NotValidType())
            }
        }
    }

    override fun stopScanning(): Flow<ResultData<Data>> {
        return ble.stopScanning().map { device->
            when(device){
                is ResultBle.Error -> ResultData.Error(throwable = ThrowableDS.extract(t = device.throwable))
                is ResultBle.Nothing -> ResultData.Success(LongDb(0L))
                else -> ResultData.Success(BooleanDb(true))
            }
        }
    }

    override fun connectDevice(adr: Data): Flow<ResultData<Data>> {
        return ble.connectDevice(adr).map {
            when(it){
                is ResultBle.Error -> ResultData.Error(throwable = ThrowableDS.extract(t = it.throwable))
                is ResultBle.Nothing -> ResultData.Success(LongDb(0L))
                else -> ResultData.Success(BooleanDb(true))
            }
        }
    }

    override fun clearCache(): Flow<ResultData<Data>> {
        return ble.onClearCacheBLE().map {
            when (it) {
                is ResultBle.Error -> ResultData.Error(throwable = ThrowableDS.extract(t = it.throwable))
                is ResultBle.Nothing -> ResultData.Success(LongDb(0L))
                else -> ResultData.Success(BooleanDb(true))
            }
        }
    }

    override fun getStateBle(): Flow<ResultData<Data>> {
        return ble.getStateBle().map {
            when (it) {
                is ResultBle.Error -> ResultData.Error(throwable = ThrowableDS.extract(t = it.throwable))
                is ResultBle.Nothing -> ResultData.Success(LongDb(0L))
                is ResultBle.ConnectingStateT -> ResultData.Success(LongDb(it.connectState.ordinal.toLong()))
                else -> ResultData.Success(BooleanDb(true))
            }
        }
    }

    override fun getHeartRate(): Flow<ResultData<Data>> {
        return ble.getHeartRate().map {
            when (it) {
                is ResultBle.Error -> ResultData.Error(throwable = ThrowableDS.extract(t = it.throwable))
                is ResultBle.Nothing -> ResultData.Success(LongDb(0L))
                is ResultBle.IntT -> ResultData.Success(LongDb(it.value.toLong()))
                else -> ResultData.Success(LongDb(0L))
            }
        }
    }
}