package com.count_out.domain.core

import com.count_out.domain.entity.Settings
import com.count_out.domain.entity.router.DeviceBle
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.throwable.ThrowableUC
import com.count_out.domain.entity.types_domai.BooleanDm
import com.count_out.domain.entity.types_domai.StringDm
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.repository.BluetoothRepo
import com.count_out.domain.repository.plans.SettingsRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BluetoothCore @Inject constructor(
    private val repo: BluetoothRepo, private val repoSetting: SettingsRepo): Core() {

    val lastBleAddress = MutableStateFlow("")

    fun startScanning(): Flow<ResultDomain<Domain>>{
        return repo.startScanning() }
    fun stopScanning(): Flow<ResultDomain<Domain>>{
        return repo.stopScanning() }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun connectDeviceHr(): Flow<ResultDomain<Domain>>{
        return lastBleAddress.flatMapConcat { lastBleAddress->
            if (lastBleAddress.isNotEmpty())
                repo.connectDevice(StringDm(lastBleAddress))
            else flowOf (ResultDomain.Success(BooleanDm(false)))
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun lastDevice(): Flow<ResultDomain<Domain>>{
        return repo.lastDevice().map { device->
            if (device is ResultDomain.Success && device.data is DeviceBle && device.data.address.isNotEmpty()) {
                    lastBleAddress.value = device.data.address
            }
            device
        }
    }

    fun clearCache(): Flow<ResultDomain<Domain>>{
        return repo.clearCache() }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun selectDeice(device: Domain): Flow<ResultDomain<Domain>>{
        return repo.stopScanning().flatMapConcat { stopScanning->
            if (stopScanning is ResultDomain.Error) flowOf(stopScanning)
            else{
                if (device is DeviceBle) {
                    combine(
                        repoSetting.saveSetting(Settings.NameBle(device.name)),
                        repoSetting.saveSetting(Settings.AddressBle(device.address)),
                    ) { f1, f2->
                        f1 as? ResultDomain.Error ?: if (f2 is ResultDomain.Error) f2
                        else {
                            lastBleAddress.value = device.address
                            ResultDomain.Success(BooleanDm(true))
                        }
                    }
                } else flowOf(ResultDomain.Error(throwable = ThrowableUC.NotValidType()))
            }
        }
    }
    fun getStateBle(): Flow<ResultDomain<Domain>>{
        return repo.getStateBle() }
    fun getHeartRate(): Flow<ResultDomain<Domain>>{
        return repo.getHeartRate() }
}