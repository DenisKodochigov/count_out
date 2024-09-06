package com.example.count_out.service.bluetooth

import android.bluetooth.BluetoothAdapter
import android.content.Context
import com.example.count_out.entity.SendToUI
import com.example.count_out.entity.StateScanner
import com.example.count_out.entity.bluetooth.BleStates
import com.example.count_out.permission.PermissionApp
import com.example.count_out.service.bluetooth.scanner.ScannerBleAll
import com.example.count_out.service.stopwatch.TimerMy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BleScanner @Inject constructor(
    val context: Context,
    val bluetoothAdapter: BluetoothAdapter,
    private val permissionApp: PermissionApp
) {
    private val timer = TimerMy()
    private val scannerBleAll = ScannerBleAll(bluetoothAdapter, permissionApp)
    private val timeScanning = 120

    /** Scan all device*/
    fun startScannerBLEDevices(sendToUi: MutableStateFlow<SendToUI>, bleStates: BleStates) {
        CoroutineScope(Dispatchers.Default).launch {
            scannerBleAll.startScannerBLEDevices(bleStates, sendToUi)
            timer.start(
                sec = timeScanning,
                endCommand = {
                    bleStates.stateScanner = StateScanner.END
                    scannerBleAll.stopScannerBLEDevices(sendToUi)
                }
            )
        }
    }
    fun stopScannerBLEDevices(sendToUi: MutableStateFlow<SendToUI>) {
        timer.cancel()
        scannerBleAll.stopScannerBLEDevices(sendToUi)
    }
}
