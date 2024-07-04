package com.example.count_out.service.bluetooth.scanner

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanFilter
import android.os.ParcelUuid
import com.example.count_out.entity.Const
import com.example.count_out.permission.PermissionApp
import com.example.count_out.service.bluetooth.objectScanCallback
import com.example.count_out.service.bluetooth.scanSettings
import com.example.count_out.ui.view_components.lg
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.UUID

class ScannerBleAll(
    private val bluetoothAdapter: BluetoothAdapter,
    val permissionApp: PermissionApp
){
    private val bleScanner by lazy { bluetoothAdapter.bluetoothLeScanner }
    val devices: MutableStateFlow<List<BluetoothDevice>> = MutableStateFlow(emptyList())

    private fun scanFilters(): List<ScanFilter> {
        val filters = mutableListOf<ScanFilter>()
        for(serviceUUID in Const.serviceUUIDs){
            val filter = ScanFilter.Builder().setServiceUuid(
                ParcelUuid(serviceUUID),
                ParcelUuid(UUID.fromString("11111111-0000-0000-0000-000000000000"))
            ).build()
            filters.add(filter)
        }
        return filters
    }
    private val scanCallback = objectScanCallback(permissionApp, devices)
    @SuppressLint("MissingPermission")
    fun startScannerBLEDevices() {
        devices.value = emptyList()
        permissionApp.checkBleScan{
            bleScanner.startScan( scanFilters(), scanSettings(0L), scanCallback) }
    }
    @SuppressLint("MissingPermission")
    fun stopScannerBLEDevices(){
        lg("Stop scanner")
        permissionApp.checkBleScan{ bleScanner.stopScan(scanCallback)}
    }
}