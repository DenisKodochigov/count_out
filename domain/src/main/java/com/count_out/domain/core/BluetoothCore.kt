package com.count_out.domain.core

import android.util.Log
import com.count_out.domain.entity.Setting
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.throwable.ThrowableUC
import com.count_out.domain.repository.BluetoothRepo
import com.count_out.domain.repository.plans.SettingsRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BluetoothCore @Inject constructor(
    private val repo: BluetoothRepo, private val repoSet: SettingsRepo): Core() {

    val lastBleAddress = MutableStateFlow("")

    fun lastDeviceAddress(){
        repo.lastDeviceAddress().map { adr->
            Log.d("KDS", "lastDeviceAddress $adr")
            if (adr is ResultUC.Success && adr.data is TypeRepo.DeviceUIT && adr.data.item.address.isNotEmpty()) {
                lastBleAddress.value = adr.data.item.address
                repo.connectDevice(TypeRepo.StringT(adr.data.item.address))
            }
        } }
    fun startScanning(): Flow<ResultUC<TypeRepo>>{
        return repo.startScanning() }
    fun stopScanning(): Flow<ResultUC<TypeRepo>>{
        return repo.stopScanning() }
    fun connectDevice(address: TypeRepo): Flow<ResultUC<TypeRepo>>{
        return repo.connectDevice(address)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun lastDevice(): Flow<ResultUC<TypeRepo>>{
        return repo.lastDevice().map { name->
            if (name is ResultUC.Success ) lastDeviceAddress()
            name
        }
    }
    fun clearCache(): Flow<ResultUC<TypeRepo>>{
        return repo.clearCache() }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun selectDeice(device: TypeRepo): Flow<ResultUC<TypeRepo>>{
        return if (device is TypeRepo.DeviceUIT) {
            val adr = TypeRepo.SettingT(Setting.BleAddress(device.item.address))
            val name = TypeRepo.SettingT(Setting.BleName(device.item.name))
            combine(
                repoSet.saveSetting(adr),
                repoSet.saveSetting(name)
            ) { f1, f2->
                f1 as? ResultUC.Error
                    ?: (f2 as? ResultUC.Error
                        ?: ResultUC.Success(TypeRepo.BooleanT(true)))
            }.flatMapConcat { res->
                if (res is ResultUC.Success) connectDevice(TypeRepo.StringT(device.item.address))
                else flow { emit(res) } }
        } else flow { emit(ResultUC.Error(throwable = ThrowableUC.NotValidType())) }
    }
    fun getStateBle(): Flow<ResultUC<TypeRepo>>{
        return repo.getStateBle() }
    fun getHeartRate(): Flow<ResultUC<TypeRepo>>{
        return repo.getHeartRate() }
}