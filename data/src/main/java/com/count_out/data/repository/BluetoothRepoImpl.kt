package com.count_out.data.repository

import com.count_out.data.models.ResultData.Companion.convertorFlow
import com.count_out.data.models.entity.StringDb
import com.count_out.data.source.framework.BleSource
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.repository.BluetoothRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BluetoothRepoImpl @Inject constructor(
    private val bleSource: BleSource,
//    private val storeDevice: SettingsSource
): BluetoothRepo {
    override fun startScanning(): Flow<ResultDomain<Domain>> {
        return bleSource.startScanning().convertorFlow() }

    override fun stopScanning(): Flow<ResultDomain<Domain>>{
        return bleSource.stopScanning().convertorFlow() }

    override fun connectDevice(address: Domain): Flow<ResultDomain<Domain>>{
        return bleSource.connectDevice(StringDb.fromDomain(address)).convertorFlow() }

//    override fun lastDevice(): Flow<ResultDomain<Domain>> {
//        return combine(
//            storeDevice.getBleName(),
//            storeDevice.getBleAddress()
//        ) { f1, f2 ->
//            when {
//                f1 !is ResultData.Success -> f1
//                f2 !is ResultData.Success -> f2
//                f1.data !is StringDb || f2.data !is StringDb ->
//                    ResultData.Error(ThrowableDS.ErrorBleDeviceName())
//                else -> ResultData.Success(
//                    object : DeviceBleDb {
//                        override val name = f1.data.item
//                        override val address = f2.data.item
////                        override fun toResultData(): ResultData<Data> =
////                            ResultData.Success(BooleanDb(true))
//                        override fun toDomain(ind: Int) = object : Domain {}
//                    }
//                )
//            }
//        }.convertorFlow()
//    }

    override fun clearCache(): Flow<ResultDomain<Domain>> {
        return bleSource.clearCache().convertorFlow()}

    override fun getStateBle(): Flow<ResultDomain<Domain>> {
        return bleSource.getStateBle().convertorFlow() }

    override fun getHeartRate(): Flow<ResultDomain<Domain>> {
        return bleSource.getHeartRate().convertorFlow() }
}
//    @OptIn(ExperimentalCoroutinesApi::class)
//    override fun selectDeice(device: Domain): Flow<ResultUC<Domain>> {
//        return if (device is Domain.DeviceUIT) {
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