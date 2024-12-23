package com.example.count_out.data.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import com.example.count_out.R
import com.example.count_out.data.bluetooth.modules.BleConnection
import com.example.count_out.data.bluetooth.modules.BleDevice
import com.example.count_out.data.bluetooth.modules.BleStates
import com.example.count_out.domain.router.DataForBle
import com.example.count_out.domain.router.DataFromBle
import com.example.count_out.entity.ConnectState
import com.example.count_out.entity.ErrorBleService
import com.example.count_out.entity.MessageApp
import com.example.count_out.entity.StateBleConnecting
import com.example.count_out.entity.StateBleScanner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Bluetooth @Inject constructor(
    private val bleScanner: BleScanner,
    private val bleConnecting: BleConnecting,
    val messageApp: MessageApp,
    val bluetoothAdapter: BluetoothAdapter) {
    val state = BleStates()

    fun startScanning(dataFromBle: DataFromBle){
        disconnectDevice()
        stopScanning(dataFromBle)
        if (state.stateBleScanner == StateBleScanner.END){
            messageApp.messageApi(R.string.start_scanner)
            state.stateBleScanner = StateBleScanner.RUNNING
            bleScanner.startScannerBLEDevices(dataFromBle, state)
        }
    }

    fun stopScanning(dataFromBle: DataFromBle){
        if (state.stateBleScanner == StateBleScanner.RUNNING){
            state.stateBleScanner = StateBleScanner.END
            bleScanner.stopScannerBLEDevices(dataFromBle)
            messageApp.messageApi(R.string.stop_scanner)
        }
    }

    fun connectDevice(dataFromBle: DataFromBle, dataForBle: DataForBle){
        if (state.stateBleScanner == StateBleScanner.RUNNING) stopScanning(dataFromBle)
        dataFromBle.connectingState.value = ConnectState.CONNECTING
        getRemoteDevice(bluetoothAdapter, dataForBle, dataFromBle, state)
        sendHeartRate(bleConnecting.heartRate, dataFromBle)
        if ( dataForBle.currentConnection != null ) { bleConnecting.connectDevice(state, dataForBle) }
    }

    private fun sendHeartRate(heartRate: MutableStateFlow<Int>, dataFromBle: DataFromBle){
        CoroutineScope(Dispatchers.Default).launch {
            heartRate.collect{ hr->
                dataFromBle.heartRate.value = hr
                if ( hr > 0) dataFromBle.connectingState.value = ConnectState.CONNECTED
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
        if (bleStates.stateBleScanner == StateBleScanner.END){
            bluetoothAdapter.let { adapter ->
                try {
                    adapter.getRemoteDevice(dataForBle.addressForSearch)?.let { dv ->
                        dataForUi.lastConnectHearthRateDevice.value = BleDevice().fromBluetoothDevice(dv)
                        dataForBle.currentConnection = BleConnection(device = dv)
                        bleStates.stateBleConnecting = StateBleConnecting.GET_REMOTE_DEVICE
                        return true
                    }
                } catch (exception: IllegalArgumentException) {
                    messageApp.errorApi("Device not found with provided address. $exception")
                    bleStates.error = ErrorBleService.GET_REMOTE_DEVICE
                }
            }
        } else { messageApp.messageApi("Running scanner.") }
        return false
    }
    fun disconnectDevice(){ bleConnecting.disconnectDevice() }
    fun onClearCacheBLE(){ bleConnecting.clearServicesCache() }
}