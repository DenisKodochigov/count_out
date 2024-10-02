package com.example.count_out.service_count_out.bluetooth

import android.bluetooth.BluetoothAdapter
import com.example.count_out.entity.BleTask
import com.example.count_out.entity.ConnectState
import com.example.count_out.entity.DataForServ
import com.example.count_out.entity.DataForUI
import com.example.count_out.entity.ErrorBleService
import com.example.count_out.entity.MessageApp
import com.example.count_out.entity.RunningState
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

    fun receiveHR(){}

    fun startScanning(dataForUi: DataForUI){
        stopScanning(dataForUi)
        if (state.stateBleScanner == StateBleScanner.END){
            state.stateBleScanner = StateBleScanner.RUNNING
            bleScanner.startScannerBLEDevices(dataForUi, state)
            messageApp.messageApi("Start scanner.")
        }
    }

    fun stopScanning(dataForUi: DataForUI){
        if (state.stateBleScanner == StateBleScanner.RUNNING){
            state.stateBleScanner = StateBleScanner.END
            bleScanner.stopScannerBLEDevices(dataForUi)
            messageApp.messageApi("Stop scanner.")
        }
    }

    fun connectDevice(dataForUI: DataForUI, dataForBle: DataForServ){
        if (state.stateBleScanner == StateBleScanner.RUNNING) stopScanning(dataForUI)

        dataForUI.connectingState.value = ConnectState.CONNECTING
        state.task = BleTask.CONNECT_DEVICE
        getRemoteDevice(bluetoothAdapter, dataForBle, dataForUI, state)
        sendHeartRate(bleConnecting.heartRate, dataForUI)
        if ( dataForBle.currentConnection != null ) {
            bleConnecting.connectDevice(state, dataForBle)
        }
    }

    private fun sendHeartRate(heartRate: MutableStateFlow<Int>, dataForUi: DataForUI){
        CoroutineScope(Dispatchers.Default).launch {
            heartRate.collect{ hr->
                if(dataForUi.runningState.value == RunningState.Stopped) return@collect
                dataForUi.heartRate.value = hr
                if ( hr > 0) dataForUi.connectingState.value = ConnectState.CONNECTED
            }
        }
    }
    private fun getRemoteDevice(
        bluetoothAdapter: BluetoothAdapter,
        dataForBle: DataForServ,
        dataForUi: DataForUI,
        bleStates: BleStates,
    ): Boolean {
        var result = false
        if (bleStates.stateBleScanner == StateBleScanner.END){
            bluetoothAdapter.let { adapter ->
                try {
                    adapter.getRemoteDevice(dataForBle.addressForSearch)?.let { dv ->
                        dataForUi.lastConnectHearthRateDevice.value = BleDevice().fromBluetoothDevice(dv)
                        dataForBle.currentConnection = BleConnection(device = dv)
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