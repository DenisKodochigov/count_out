package com.count_out.device.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.util.Log
import com.count_out.data.models.throwable.TypeSource
import com.count_out.device.bluetooth.models.BleConnectionImpl
import com.count_out.device.bluetooth.models.ResultBle
import com.count_out.device.bluetooth.models.ThrowableBle
import com.count_out.domain.entity.Setting
import com.count_out.domain.entity.enums.ConnectState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Запускаем сразу сканрование. Предполагаем, что если сканирование уже запущено, то вывалится ошибка
 */
@Singleton
class Bluetooth @Inject constructor(
    private val bleScanner: BleScanner,
    private val bleConnecting: BleConnecting,
    private val bluetoothAdapter: BluetoothAdapter,
) {
    private var currentConnection = BleConnectionImpl()

    fun startScanning(): Flow<ResultBle> {
        if (!bluetoothAdapter.isEnabled) return flow { emit(
            ResultBle.Error(throwable = ThrowableBle.NotValidBle())) }
        return try {
            disconnectDevice()
            bleScanner.stopScanner()
            bleScanner.startScanner()
        } catch (e: Exception){ flow { emit(
            ResultBle.Error(throwable = ThrowableBle.extract(t = e))) }
        }
    }
    fun stopScanning(): Flow<ResultBle> = bleScanner.stopScanner()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun connectDevice(adr: TypeSource): Flow<ResultBle>  {
        if (!bluetoothAdapter.isEnabled) return flow { emit(
            ResultBle.Error(throwable = ThrowableBle.NotValidBle())) }
        return if (adr is TypeSource.SettingT && adr.item is Setting.BleAddress){
            try {
                disconnectDevice()
                Log.d("KDS", "connectDevice")
                bleScanner.stopScanner().flatMapConcat{ it1->
                    if (it1 is ResultBle.Error) flow { emit(it1) } else {
                        getRemoteDevice( (adr.item as Setting.BleAddress).value).flatMapConcat{ it2->
                            if (it2 is ResultBle.Error) flow { emit(it2) } else {
                                bleConnecting.connectDevice(currentConnection)
//                                    .flatMapConcat{it3->
//                                    if (it3 is ResultBle.Error) flow { emit(it3) } else {
//                                        bleConnecting.heartRate.collect { ResultBle.HeartRate(it) }
////                                        bleConnecting.heartRate.map { ResultBle.HeartRate(it) }
//                                    }
//                                }
                            }
                        }
                    }
                }
            } catch (e: Exception){ flow { emit(
                ResultBle.Error(throwable = ThrowableBle.extract(t = e))) } }
        } else flow { emit(ResultBle.Error(throwable = ThrowableBle.NotValidType())) }
//        runBlocking {
//            result.collect { Log.d("KDS","connectDevice $it") }
//        }

    }

    @SuppressLint("MissingPermission")
    private fun getRemoteDevice(address: String): Flow<ResultBle> {
        Log.d("KDS","getRemoteDevice")
        return flow { emit(
            try {
                bluetoothAdapter.getRemoteDevice(address)?.let { dv ->
                    currentConnection = BleConnectionImpl(device = dv)
                    ResultBle.BooleanT(true)
                } ?: ResultBle.Error(ThrowableBle.NotValidBle())
            } catch (e: IllegalArgumentException) { ResultBle.Error(ThrowableBle.extract(t=e)) }
        ) }
    }

    fun disconnectDevice() {
        bleConnecting.disconnectDevice()
    }

    fun onClearCacheBLE(): Flow<ResultBle> {
        ConnectState.entries[0]
        return flow { emit(
            if (bleConnecting.clearServicesCache()) ResultBle.BooleanT(true)
            else ResultBle.Error(throwable = ThrowableBle.ClearCache())
        ) }
    }
    fun getStateBle(): Flow<ResultBle> {
        return bleConnecting.connection.map { ResultBle.ConnectingStateT(ConnectState.entries[it.newState]) }
    }
    fun getHeartRate(): Flow<ResultBle> {
        return bleConnecting.heartRate.map { ResultBle.IntT( it) }
    }

//    private fun sendHeartRate(heartRate: MutableStateFlow<Int>, dataFromBle: DataFromBle): Flow<ResultBle> {
//        CoroutineScope(Dispatchers.Default).launch {
//            heartRate.collect { hr ->
//                dataFromBle.heartRate.value = hr
//                if (hr > 0) dataFromBle.connectingState.value = ConnectState.CONNECTED
//            }
//        }
//    }

}

//private val state = BleStates()
//
//private fun checkBluetoothEnable(): Boolean {
//    permission.checkBle { if ( !bluetoothAdapter.isEnabled) {
//        //message Bluetooth power off
//    } }
//    return bluetoothAdapter.isEnabled
//}
//
//fun startScanning(dataFromBle: DataFromBle){
//    if (! checkBluetoothEnable()) return
//    disconnectDevice()
//    stopScanning(dataFromBle)
//    if (state.stateBleScanner.value == RunningState.Stopped){
////            messageApp.messageApi(R.string.start_scanner)
//        state.stateBleScanner.value = RunningState.Started
//        bleScanner.startScannerBLEDevices(dataFromBle, state)
//    }
//}
//
//fun stopScanning(dataFromBle: DataFromBle){
//    if (! checkBluetoothEnable()) return
//    if (state.stateBleScanner.value == RunningState.Started){
//        state.stateBleScanner.value = RunningState.Stopped
//        bleScanner.stopScannerBLEDevices(dataFromBle)
////            messageApp.messageApi(R.string.stop_scanner)
//    }
//}
//
//fun connectDevice(dataFromBle: DataFromBle, dataForBle: DataForBle){
//    if (! checkBluetoothEnable()) return
//    if (state.stateBleScanner.value == RunningState.Started) stopScanning(dataFromBle)
//    dataFromBle.connectingState.value = ConnectState.CONNECTING
//    getRemoteDevice(bluetoothAdapter, dataForBle, dataFromBle, state)
//    sendHeartRate(bleConnecting.heartRate, dataFromBle)
//    if ( dataForBle.currentConnection == null ) { bleConnecting.connectDevice(state, dataForBle) }
//}
//
//private fun sendHeartRate(heartRate: MutableStateFlow<Int>, dataFromBle: DataFromBle){
//    if (! checkBluetoothEnable()) return
//    CoroutineScope(Dispatchers.Default).launch {
//        heartRate.collect{ hr->
//            dataFromBle.heartRate.value = hr
//            if ( hr > 0) dataFromBle.connectingState.value = ConnectState.CONNECTED
//        }
//    }
//}
//@SuppressLint("MissingPermission")
//private fun getRemoteDevice(
//    bluetoothAdapter: BluetoothAdapter,
//    dataForBle: DataForBle,
//    dataForUi: DataFromBle,
//    bleStates: BleStates,
//): Boolean {
//    if (! checkBluetoothEnable()) return false
//    if (bleStates.stateBleScanner.value == RunningState.Stopped){
//        bluetoothAdapter.let { adapter ->
//            try {
//                adapter.getRemoteDevice(dataForBle.addressForSearch)?.let { dv ->
//                    dataForUi.lastConnectHearthRateDevice.value = BleDeviceImpl().fromBluetoothDevice(dv)
//                    dataForBle.currentConnection = BleConnectionImpl(device = dv)
//                    bleStates.stateBleConnecting = StateBleConnecting.GET_REMOTE_DEVICE
//                    return true
//                }
//            } catch (exception: IllegalArgumentException) {
////                    messageApp.errorApi("Device not found with provided address. $exception")
//                bleStates.error = ErrorBleService.GET_REMOTE_DEVICE
//            }
//        }
//    } else {
////            messageApp.messageApi("Running scanner.")
//    }
//    return false
//}
//fun disconnectDevice(){ bleConnecting.disconnectDevice() }
//fun onClearCacheBLE(){ bleConnecting.clearServicesCache() }