package com.count_out.data.repository

import com.count_out.data.source.framework.BleSource
import com.count_out.data.source.local.SettingsSource
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.router.DeviceBle
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.BluetoothRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import javax.inject.Inject

class BluetoothRepoImpl @Inject constructor(
    private val bleSource: BleSource,
    private val storeDevice: SettingsSource
): BluetoothRepo, PrimeRepo() {
    override fun startScanning(): Flow<ResultUC<TypeRepo>> {
        return bleSource.startScanning().convertor() }

    override fun stopScanning(): Flow<ResultUC<TypeRepo>>{
        return bleSource.stopScanning().convertor() }

    override fun connectDevice(address: TypeRepo): Flow<ResultUC<TypeRepo>>{
        return bleSource.connectDevice(toTypeSource(address)).convertor() }

    override fun lastDevice(): Flow<ResultUC<TypeRepo>>{
        return combine(
            storeDevice.getBleName(),
            storeDevice.getBleAddress()
        ){ f1, f2 ->
            f1 as? ResultUC.Error
                ?: (f2 as? ResultUC.Error
                    ?: object: DeviceBle { override val name: String = ""; override val address: String = "" })
        }.flatMapConcat { res->

        }


        storeDevice.getBleName().convertor() }

    override fun clearCache(): Flow<ResultUC<TypeRepo>> {
        return bleSource.clearCache().convertor()}

    override fun getStateBle(): Flow<ResultUC<TypeRepo>> {
        return bleSource.getStateBle().convertor() }

    override fun getHeartRate(): Flow<ResultUC<TypeRepo>> {
        return bleSource.getHeartRate().convertor() }
}
//    @OptIn(ExperimentalCoroutinesApi::class)
//    override fun selectDeice(device: TypeRepo): Flow<ResultUC<TypeRepo>> {
//        return if (device is TypeRepo.DeviceUIT) {
//            val adr = TypeSource.SettingT(Setting.BleAddress(device.item.address))
//            val name = TypeSource.SettingT(Setting.BleName(device.item.name))
//            combine(
//                storeDevice.saveBleAddress(adr),
//                storeDevice.saveBleName(name)
//               ) { f1, f2->
//                f1 as? ResultSource.Error
//                    ?: (f2 as? ResultSource.Error
//                        ?: ResultSource.Success(TypeSource.BooleanT(true)))
//               }.flatMapConcat { res->
//                   if (res is ResultSource.Success) bleSource.connectDevice(adr)
//                   else flow { emit(res) } }
//                   .convertor()
//        } else flow { emit(ResultUC.Error(throwable = ThrowableUC.NotValidType())) }
//    }