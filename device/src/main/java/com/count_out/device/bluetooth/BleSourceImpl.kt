package com.count_out.device.bluetooth

import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.models.throwable.TypeSource
import com.count_out.data.source.PrimeSource
import com.count_out.data.source.framework.BleSource
import com.count_out.device.bluetooth.models.ResultBle
import com.count_out.domain.entity.router.DeviceBle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BleSourceImpl @Inject constructor(private val ble: Bluetooth): BleSource, PrimeSource() {
    override fun startScanning(): Flow<ResultSource<TypeSource>> {
        val mapDevice: MutableMap<String,DeviceBle> = mutableMapOf()
        return ble.startScanning().map { devUI->
            when(devUI){
                is ResultBle.Error -> ResultSource.Error(throwable = ThrowableDS.extract(t = devUI.throwable))
                is ResultBle.Nothing -> ResultSource.Success(TypeSource.NullT)
                is ResultBle.Device -> {
                    mapDevice.put(devUI.device.address, devUI.device)
                    ResultSource.Success(TypeSource.DevicesUIT(mapDevice as Map<String, DeviceBle>))
                }
                else -> ResultSource.Error(throwable = ThrowableDS.NotValidType())
            }
        }
    }

    override fun stopScanning(): Flow<ResultSource<TypeSource>> {
        return ble.stopScanning().map { device->
            when(device){
                is ResultBle.Error -> ResultSource.Error(throwable = ThrowableDS.extract(t = device.throwable))
                is ResultBle.Nothing -> ResultSource.Success(TypeSource.NullT)
                else -> ResultSource.Success(TypeSource.BooleanT(true))
            }
        }
    }

    override fun connectDevice(adr: TypeSource): Flow<ResultSource<TypeSource>> {
        return ble.connectDevice(adr).map {
            when(it){
                is ResultBle.Error -> ResultSource.Error(throwable = ThrowableDS.extract(t = it.throwable))
                is ResultBle.Nothing -> ResultSource.Success(TypeSource.NullT)
                else -> ResultSource.Success(TypeSource.BooleanT(true))
            }
        }
    }

    override fun clearCache(): Flow<ResultSource<TypeSource>> {
        return ble.onClearCacheBLE().map {
            when (it) {
                is ResultBle.Error -> ResultSource.Error(throwable = ThrowableDS.extract(t = it.throwable))
                is ResultBle.Nothing -> ResultSource.Success(TypeSource.NullT)
                else -> ResultSource.Success(TypeSource.BooleanT(true))
            }
        }
    }

    override fun getStateBle(): Flow<ResultSource<TypeSource>> {
        return ble.getStateBle().map {
            when (it) {
                is ResultBle.Error -> ResultSource.Error(throwable = ThrowableDS.extract(t = it.throwable))
                is ResultBle.Nothing -> ResultSource.Success(TypeSource.NullT)
                is ResultBle.ConnectingStateT -> ResultSource.Success(
                    TypeSource.BleConnectStateT(it.connectState))
                else -> ResultSource.Success(TypeSource.BooleanT(true))
            }
        }
    }

    override fun getHeartRate(): Flow<ResultSource<TypeSource>> {
        return ble.getHeartRate().map {
            when (it) {
                is ResultBle.Error -> ResultSource.Error(throwable = ThrowableDS.extract(t = it.throwable))
                is ResultBle.Nothing -> ResultSource.Success(TypeSource.NullT)
                is ResultBle.IntT -> ResultSource.Success(TypeSource.IntT(it.value))
                else -> ResultSource.Success(TypeSource.IntT(0))
            }
        }
    }
}