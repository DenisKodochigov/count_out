package com.example.count_out.service.bluetooth.scanner

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanFilter
import com.example.count_out.permission.PermissionApp
import com.example.count_out.service.bluetooth.objectScanCallback
import com.example.count_out.service.bluetooth.scanSettings
import com.example.count_out.ui.view_components.lg
import kotlinx.coroutines.flow.MutableStateFlow

class ScannerBleByMac(
    private val bluetoothAdapter: BluetoothAdapter,
    private val permissionApp: PermissionApp
) {
    private val bleScanner by lazy { bluetoothAdapter.bluetoothLeScanner }
    val devices: MutableStateFlow<List<BluetoothDevice>> = MutableStateFlow(emptyList())
    private val scanCallbackByMac = objectScanCallback(permissionApp, devices)

    @SuppressLint("MissingPermission")
    fun startScannerBLEDevices(mac: String){
        devices.value = emptyList()
        if (mac != "") {
            val scanFilterMac = listOf(ScanFilter.Builder().setDeviceAddress(mac).build())
            permissionApp.checkBleScan{
                bleScanner.startScan( scanFilterMac, scanSettings(0L), scanCallbackByMac) }
        }
    }
    @SuppressLint("MissingPermission")
    fun stopScannerBLEDevices(){
        lg("Stop scanner ByMac")
        permissionApp.checkBleScan{ bleScanner.stopScan(scanCallbackByMac)}
    }
}