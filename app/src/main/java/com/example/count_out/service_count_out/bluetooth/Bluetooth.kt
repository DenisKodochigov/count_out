package com.example.count_out.service_count_out.bluetooth

import android.bluetooth.BluetoothAdapter
import com.example.count_out.entity.BleTask
import com.example.count_out.entity.ConnectState
import com.example.count_out.entity.ErrorBleService
import com.example.count_out.entity.MessageApp
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.SendToService
import com.example.count_out.entity.SendToUI
import com.example.count_out.entity.StateBleConnecting
import com.example.count_out.entity.StateBleScanner
import com.example.count_out.entity.bluetooth.BleConnection
import com.example.count_out.entity.bluetooth.BleDevice
import com.example.count_out.entity.bluetooth.BleStates
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Bluetooth @Inject constructor(
    val bleScanner: BleScanner,
    val messageApp: MessageApp,
    val bleConnecting: BleConnecting,
    val bluetoothAdapter: BluetoothAdapter) {

    val state = BleStates()

    fun startScanning(){}

    fun stopScanning(){}

    fun receiveHR(){}

    fun startScannerBLEDevices(sendToUi: SendToUI){
        if (state.stateBleScanner == StateBleScanner.END){
            state.stateBleScanner = StateBleScanner.RUNNING
            bleScanner.startScannerBLEDevices(sendToUi, state)
            messageApp.messageApi("Start scanner.")
        }
    }

    fun stopScannerBLEDevices(sendToUi: SendToUI){
        if (state.stateBleScanner == StateBleScanner.RUNNING){
            state.stateBleScanner = StateBleScanner.END
            bleScanner.stopScannerBLEDevices(sendToUi)
            messageApp.messageApi("Stop scanner.")
        }
    }

    fun connectDevice(sendToUi: SendToUI, sendToBle: SendToService){
        sendToUi.connectingStateF.value = ConnectState.CONNECTING
        state.task = BleTask.CONNECT_DEVICE
        getRemoteDevice(bluetoothAdapter, sendToBle, sendToUi, state)
        sendHeartRate(bleConnecting.heartRate, sendToUi)
        if ( sendToBle.currentConnection != null ) {
            bleConnecting.connectDevice(state, sendToBle)
        }
    }

    private fun sendHeartRate(heartRate: MutableStateFlow<Int>, sendToUi: SendToUI){
        CoroutineScope(Dispatchers.Default).launch {
            heartRate.collect{ hr->
                if(sendToUi.runningState.value == RunningState.Stopped) return@collect
                sendToUi.heartRateF.value = hr
//                sendToUi.update { send-> send.copy( heartRate = hr,) }
                if ( hr > 0) sendToUi.connectingStateF.value = ConnectState.CONNECTED
//                if ( hr > 0) sendToUi.update { send-> send.copy( connectingState = ConnectState.CONNECTED) }
            }
        }
    }
    private fun getRemoteDevice(
        bluetoothAdapter: BluetoothAdapter,
        sendToBle: SendToService,
        sendToUi: SendToUI,
        bleStates: BleStates,
    ): Boolean {
        var result = false
        if (bleStates.stateBleScanner == StateBleScanner.END){
            bluetoothAdapter.let { adapter ->
                try {
                    adapter.getRemoteDevice(sendToBle.addressForSearch)?.let { dv ->
                        sendToUi.lastConnectHearthRateDeviceF.value = BleDevice().fromBluetoothDevice(dv)
//                        sendToUi.update { send->
//                            send.copy(lastConnectHearthRateDevice = BleDevice().fromBluetoothDevice(dv)) }
                        sendToBle.currentConnection = BleConnection(device = dv)
                        result = true
                        bleStates.stateBleConnecting = StateBleConnecting.GET_REMOTE_DEVICE
                    }
                } catch (exception: IllegalArgumentException) {
                    messageApp.errorApi("Device not found with provided address. $exception")
                    bleStates.error = ErrorBleService.GET_REMOTE_DEVICE
                }
            }
        } else {
            messageApp.messageApi("Running scanner.")
        }
        return result
    }
    fun disconnectDevice(){ bleConnecting.disconnectDevice() }
    fun onClearCacheBLE(){ bleConnecting.clearServicesCache() }
}