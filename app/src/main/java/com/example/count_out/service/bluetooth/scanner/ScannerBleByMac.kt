package com.example.count_out.service.bluetooth.scanner

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import com.example.count_out.entity.bluetooth.BleStates
import com.example.count_out.entity.bluetooth.SendToUI
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
    fun startScannerBLEDevices(mac:String, bleStates: BleStates, sendToUI: SendToUI){
        scanCallback = objectScanCallback(bleStates, sendToUI)
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