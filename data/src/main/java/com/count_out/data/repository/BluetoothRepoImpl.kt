package com.count_out.data.repository

import android.util.Log
import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.TypeSource
import com.count_out.data.source.framework.BleSource
import com.count_out.data.source.local.SettingsSource
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.BluetoothRepo
import com.count_out.domain.repository.TypeRepo
import com.count_out.domain.entity.Setting
import com.count_out.domain.entity.throwable.ThrowableUC
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BluetoothRepoImpl @Inject constructor(
    private val bleSource: BleSource,
    private val savedDevice: SettingsSource
): BluetoothRepo, PrimeRepo() {
    override fun startScanning(): Flow<ResultUC<TypeRepo>> {
        return bleSource.startScanning().convertor() }

    override fun stopScanning(): Flow<ResultUC<TypeRepo>>{
        return bleSource.stopScanning().convertor() }

    override fun connectDevice(addr: TypeRepo): Flow<ResultUC<TypeRepo>>{
        return bleSource.connectDevice(toTypeSource(addr)).convertor() }

    override fun lastDevice(): Flow<ResultUC<TypeRepo>>{
        return savedDevice.getBleAddress().convertor() //.concat1{ bleSource.connectDevice(it) }
    }
    override fun clearCache(): Flow<ResultUC<TypeRepo>> {
        return flow { emit(ResultUC.Success(TypeRepo.BooleanT(item = true)) )} }

    override fun selectDeice(device: TypeRepo): Flow<ResultUC<TypeRepo>> {
        return if (device is TypeRepo.DeviceUIT) {
            val adr = TypeSource.SettingT(Setting.BleAddress(device.item.address))
            val name = TypeSource.SettingT(Setting.BleName(device.item.name))
            combine(
                savedDevice.saveBleAddress(adr),
                savedDevice.saveBleName(name)
               ) { f1, f2->
                   if (f1 is ResultSource.Error) f1
                   else if (f2 is ResultSource.Error) f2
                   else {
                       bleSource.connectDevice(adr)
                       ResultSource.Success(TypeSource.BooleanT(true))
                   }
               }.convertor()
        } else flow { emit(ResultUC.Error(throwable = ThrowableUC.NotValidType())) }



    }
//       return


    override fun getStateBle(): Flow<ResultUC<TypeRepo>> {
        return flow { emit(ResultUC.Success(TypeRepo.BooleanT(item = true)) )} }
}