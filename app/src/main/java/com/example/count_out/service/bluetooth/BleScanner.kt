package com.example.count_out.service.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.Context
import com.example.count_out.entity.StateScanner
import com.example.count_out.entity.TimerState
import com.example.count_out.entity.bluetooth.ValOutBleService
import com.example.count_out.permission.PermissionApp
import com.example.count_out.service.bluetooth.scanner.ScannerBleAll
import com.example.count_out.service.bluetooth.scanner.ScannerBleByMac
import com.example.count_out.service.stopwatch.Timer
import com.example.count_out.ui.view_components.lg
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BleScanner @Inject constructor(
    val context: Context,
    val bluetoothAdapter: BluetoothAdapter,
    val permissionApp: PermissionApp
) {
    private val timer = Timer()
    private val scannerBleAll = ScannerBleAll(bluetoothAdapter, permissionApp)
    private val scannerBleByMac = ScannerBleByMac(bluetoothAdapter, permissionApp)
    private val timeScanning = 120000L

    /** Scan all device*/
    @SuppressLint("MissingPermission")
    fun startScannerBLEDevices(valOut: ValOutBleService) {
        CoroutineScope(Dispatchers.Default).launch {
            if (valOut.stateScanner.value == StateScanner.END) {
                valOut.stateScanner.value = StateScanner.RUNNING
                timer.changeState(TimerState.COUNTING, timeScanning)
                scannerBleAll.startScannerBLEDevices(valOut)
            }
            timer.endCounting {
                valOut.stateScanner.value = StateScanner.END
                scannerBleAll.stopScannerBLEDevices()
                this.cancel()
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun stopScannerBLEDevices(valOut: ValOutBleService) {
        if (valOut.stateScanner.value == StateScanner.RUNNING) {
            lg("Stop scanner ByMac")
            valOut.stateScanner.value = StateScanner.END
            scannerBleAll.stopScannerBLEDevices()
        }
    }

    /** Scan device by MAC address*/
    @SuppressLint("MissingPermission")
    fun startScannerBLEDevicesByMac(mac: String, valOut: ValOutBleService) {
        if (mac != "") {
            CoroutineScope(Dispatchers.Default).launch {
                if (valOut.stateScanner.value == StateScanner.END) {
                    valOut.stateScanner.value = StateScanner.RUNNING
                    timer.changeState(TimerState.COUNTING, timeScanning)
                    scannerBleByMac.startScannerBLEDevices(mac, valOut)
                }
                timer.endCounting {
                    valOut.stateScanner.value = StateScanner.END
                    scannerBleByMac.stopScannerBLEDevices()
                    this.cancel()
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun stopScannerBLEDevicesByMac(valOut: ValOutBleService) {
        if (valOut.stateScanner.value == StateScanner.RUNNING) {
            lg("Stop scanner ByMac")
            valOut.stateScanner.value = StateScanner.END
            scannerBleByMac.stopScannerBLEDevices()
        }
    }
}
