package com.example.count_out.service.bluetooth

import android.bluetooth.BluetoothAdapter
import android.content.Context
import com.example.count_out.entity.SendToUI
import com.example.count_out.entity.StateScanner
import com.example.count_out.entity.TimerState
import com.example.count_out.entity.bluetooth.BleStates
import com.example.count_out.permission.PermissionApp
import com.example.count_out.service.bluetooth.scanner.ScannerBleAll
import com.example.count_out.service.stopwatch.Timer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
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
    private val timer = Timer()
    private val scannerBleAll = ScannerBleAll(bluetoothAdapter, permissionApp)
//    private val scannerBleByMac = ScannerBleByMac(bluetoothAdapter, permissionApp)
    private val timeScanning = 120000L

    /** Scan all device*/
    fun startScannerBLEDevices(sendToUi: MutableStateFlow<SendToUI>, bleStates: BleStates) {
        CoroutineScope(Dispatchers.Default).launch {
            timer.changeState(TimerState.COUNTING, timeScanning)
            scannerBleAll.startScannerBLEDevices(bleStates, sendToUi)
            timer.endCounting {
                bleStates.stateScanner = StateScanner.END
                scannerBleAll.stopScannerBLEDevices(sendToUi)
                this.cancel()
            }
        }
    }
    fun stopScannerBLEDevices(sendToUi: MutableStateFlow<SendToUI>) {
        timer.changeState(TimerState.END, timeScanning)
        scannerBleAll.stopScannerBLEDevices(sendToUi)
    }

    /** Scan device by MAC address*/
//    @SuppressLint("MissingPermission")
//    fun startScannerBLEDevicesByMac(sendToUi: SendToUI, receiveFromUI: ReceiveFromUI, bleStates: BleStates) {
//        CoroutineScope(Dispatchers.Default).launch {
//            timer.changeState(TimerState.COUNTING, timeScanning)
//            scannerBleByMac.startScannerBLEDevices(receiveFromUI.addressForSearch, bleStates, sendToUi)
//            timer.endCounting {
//                bleStates.stateScanner = StateScanner.END
//                scannerBleByMac.stopScannerBLEDevices()
//                this.cancel()
//            }
//        }
//    }
//
//    @SuppressLint("MissingPermission")
//    fun stopScannerBLEDevicesByMac(valOut: SendToUI) {
//        lg("Stop scanner ByMac")
//        scannerBleByMac.stopScannerBLEDevices()
//    }
}
