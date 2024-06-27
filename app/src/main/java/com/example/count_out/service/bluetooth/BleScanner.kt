package com.example.count_out.service.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context
import com.example.count_out.entity.ScannerState
import com.example.count_out.entity.TimerState
import com.example.count_out.permission.PermissionApp
import com.example.count_out.service.bluetooth.scanner.ScannerBleAll
import com.example.count_out.service.bluetooth.scanner.ScannerBleByMac
import com.example.count_out.service.stopwatch.Timer
import com.example.count_out.ui.view_components.lg
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
    val permissionApp: PermissionApp
){
    private val scannerState: MutableStateFlow<ScannerState> = MutableStateFlow(ScannerState.END)
    private val timer = Timer()
    private val scannerBleAll = ScannerBleAll(bluetoothAdapter, permissionApp)
    private val scannerBleByMac = ScannerBleByMac(bluetoothAdapter, permissionApp)
    private val timeScanning = 60000L

    /** Scan device by MAC address*/
    @SuppressLint("MissingPermission")
    fun startScannerBLEDevicesByMac(mac: String){
        if (mac != "") {
            CoroutineScope(Dispatchers.Default).launch {
                if (scannerState.value == ScannerState.END) {
                    scannerState.value = ScannerState.COUNTING
                    timer.changeState(TimerState.COUNTING, timeScanning)
                    scannerBleByMac.startScannerBLEDevices( mac )
                }
                timer.endCounting {
                    scannerState.value =ScannerState.END
                    scannerBleByMac.stopScannerBLEDevices()
                    this.cancel()
                }
            }
        }
    }
    @SuppressLint("MissingPermission")
    fun stopScannerBLEDevicesByMac(){
        if (scannerState.value == ScannerState.COUNTING) {
            lg("Stop scanner ByMac")
            scannerState.value = ScannerState.END
            scannerBleByMac.stopScannerBLEDevices()
        }
    }
    fun getDeviceByMac(): MutableStateFlow<List<BluetoothDevice>> = scannerBleByMac.getDevices()

    /** Scan all device*/
    @SuppressLint("MissingPermission")
    fun startScannerBLEDevices() {
        CoroutineScope(Dispatchers.Default).launch {
            if (scannerState.value == ScannerState.END) {
                scannerState.value = ScannerState.COUNTING
                timer.changeState(TimerState.COUNTING, timeScanning)
                scannerBleAll.startScannerBLEDevices()
            }
            timer.endCounting {
                scannerState.value =ScannerState.END
                scannerBleAll.stopScannerBLEDevices()
                this.cancel()
            }
        }
    }
    @SuppressLint("MissingPermission")
    fun stopScannerBLEDevices(){
        if (scannerState.value == ScannerState.COUNTING) {
            lg("Stop scanner ByMac")
            scannerState.value = ScannerState.END
            scannerBleAll.stopScannerBLEDevices()
        }
    }

    fun getDevices() = scannerBleAll.getDevices()
}
