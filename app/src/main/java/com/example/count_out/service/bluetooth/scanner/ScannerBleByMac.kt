package com.example.count_out.service.bluetooth.scanner

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import com.example.count_out.entity.bluetooth.ValOutBleService
import com.example.count_out.permission.PermissionApp
import com.example.count_out.service.bluetooth.objectScanCallback
import com.example.count_out.service.bluetooth.scanSettings
import com.example.count_out.ui.view_components.lg

class ScannerBleByMac(
    private val bluetoothAdapter: BluetoothAdapter,
    private val permissionApp: PermissionApp
) {
    private val bleScanner by lazy { bluetoothAdapter.bluetoothLeScanner }
    private lateinit var scanCallback: ScanCallback

    @SuppressLint("MissingPermission")
    fun startScannerBLEDevices(mac: String, valOut: ValOutBleService){
        scanCallback = objectScanCallback(valOut)
        if (mac != "") {
            val scanFilterMac = listOf(ScanFilter.Builder().setDeviceAddress(mac).build())
            permissionApp.checkBleScan{
                bleScanner.startScan( scanFilterMac, scanSettings(0L), scanCallback) }
        }
    }
    @SuppressLint("MissingPermission")
    fun stopScannerBLEDevices(){
        lg("Stop scanner ByMac")
        permissionApp.checkBleScan{ bleScanner.stopScan(scanCallback)}
    }
}