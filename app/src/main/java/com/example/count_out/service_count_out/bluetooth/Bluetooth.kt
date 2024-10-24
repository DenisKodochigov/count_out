package com.example.count_out.service_count_out.bluetooth

import android.bluetooth.BluetoothAdapter
import com.example.count_out.R
import com.example.count_out.entity.ConnectState
import com.example.count_out.entity.ErrorBleService
import com.example.count_out.entity.MessageApp
import com.example.count_out.entity.StateBleConnecting
import com.example.count_out.entity.StateBleScanner
import com.example.count_out.entity.bluetooth.BleConnection
import com.example.count_out.entity.bluetooth.BleDevice
import com.example.count_out.entity.bluetooth.BleStates
import com.example.count_out.entity.router.DataForBle
import com.example.count_out.entity.router.DataFromBle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Bluetooth @Inject constructor(
    private val bleScanner: BleScanner,
    val messageApp: MessageApp,
    private val bleConnecting: BleConnecting,
    val bluetoothAdapter: BluetoothAdapter) {

    val state = BleStates()

    fun startScanning(dataFromBle: DataFromBle){
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
        if ( dataForBle.currentConnection != null ) {
            bleConnecting.connectDevice(state, dataForBle)
        }
    }

    private fun sendHeartRate(heartRate: MutableStateFlow<Int>, dataFromBle: DataFromBle){
        CoroutineScope(Dispatchers.Default).launch {
            heartRate.collect{ hr->
                dataFromBle.heartRate.value = hr
                if ( hr > 0) dataFromBle.connectingState.value = ConnectState.CONNECTED
            }
        }
    }
    private fun getRemoteDevice(
        bluetoothAdapter: BluetoothAdapter,
        dataForBle: DataForBle,
        dataForUi: DataFromBle,
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