package com.count_out.device.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.models.throwable.TypeSource
import com.count_out.data.router.models.DataForBle
import com.count_out.data.router.models.DataFromBle
import com.count_out.device.bluetooth.models.BleConnectionImpl
import com.count_out.device.bluetooth.models.BleDeviceImpl
import com.count_out.device.bluetooth.models.BleStates
import com.count_out.device.bluetooth.models.ResultBle
import com.count_out.device.bluetooth.models.ThrowableBle
import com.count_out.device.permission.PermissionApp
import com.count_out.domain.entity.enums.ConnectState
import com.count_out.domain.entity.enums.ErrorBleService
import com.count_out.domain.entity.enums.RunningState
import com.count_out.domain.entity.enums.StateBleConnecting
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Bluetooth @Inject constructor(
    private val permission: PermissionApp,
    private val bleScanner: BleScanner,
    private val bleConnecting: BleConnecting,
    private val bluetoothAdapter: BluetoothAdapter,
) {
    private val state = BleStates()
//    private fun checkBluetoothEnable(): Boolean {
//        return bluetoothAdapter.isEnabled
//    }
    /**
     * Запускаем сразу сканрование. Предполагаем, что если сканирование уже запущено, то вывалится ошибка
     */
    fun startScanning(): Flow<ResultBle> {
        if (!bluetoothAdapter.isEnabled) return flow { emit(
            ResultBle.Error(throwable = ThrowableBle.NotValidBle())) }
        return try {
            disconnectDevice()
            bleScanner.stopScanner()
            bleScanner.startScannerBLEDevices()
        } catch (e: Exception){ flow { emit(
            ResultBle.Error(throwable = ThrowableBle.extract(t = e))) }
        }
    }

    //                val ddd = BleDeviceImpl().fromBluetoothDevice(dev)
//               dataFromBle.shareIn()    .emit(ResultBle.Device( ddd) )
//                if (dataFromBle.foundDevices.value.find { it.address == dev.address } == null){
//                    dataFromBle.foundDevices.value = dataFromBle.foundDevices.value.addApp(
//                        BleDeviceImpl().fromBluetoothDevice(dev))
//                }
    fun stopScanning(): Flow<ResultBle> = bleScanner.stopScanner()

    fun connectDevice(dataFromBle: DataFromBle, dataForBle: DataForBle) {
        if (!bluetoothAdapter.isEnabled) return
        if (state.stateBleScanner.value == RunningState.Started) stopScanning()
        dataFromBle.connectingState.value = ConnectState.CONNECTING
        getRemoteDevice(bluetoothAdapter, dataForBle, dataFromBle, state)
        sendHeartRate(bleConnecting.heartRate, dataFromBle)
        if (dataForBle.currentConnection == null) {
            bleConnecting.connectDevice(state, dataForBle)
        }
    }

    private fun sendHeartRate(heartRate: MutableStateFlow<Int>, dataFromBle: DataFromBle) {
        if (!bluetoothAdapter.isEnabled) return
        CoroutineScope(Dispatchers.Default).launch {
            heartRate.collect { hr ->
                dataFromBle.heartRate.value = hr
                if (hr > 0) dataFromBle.connectingState.value = ConnectState.CONNECTED
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getRemoteDevice(
        bluetoothAdapter: BluetoothAdapter,
        dataForBle: DataForBle,
        dataForUi: DataFromBle,
        bleStates: BleStates,
    ): Boolean {
        if (!bluetoothAdapter.isEnabled) return false
        if (bleStates.stateBleScanner.value == RunningState.Stopped) {
            bluetoothAdapter.let { adapter ->
                try {
                    adapter.getRemoteDevice(dataForBle.addressForSearch)?.let { dv ->
                        dataForUi.lastConnectHearthRateDevice.value =
                            BleDeviceImpl().fromBluetoothDevice(dv)
                        dataForBle.currentConnection = BleConnectionImpl(device = dv)
                        bleStates.stateBleConnecting = StateBleConnecting.GET_REMOTE_DEVICE
                        return true
                    }
                } catch (exception: IllegalArgumentException) {
//                    messageApp.errorApi("Device not found with provided address. $exception")
                    bleStates.error = ErrorBleService.GET_REMOTE_DEVICE
                }
            }
        } else {
//            messageApp.messageApi("Running scanner.")
        }
        return false
    }

    fun disconnectDevice() {
        bleConnecting.disconnectDevice()
    }

    fun onClearCacheBLE() {
        bleConnecting.clearServicesCache()
    }
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