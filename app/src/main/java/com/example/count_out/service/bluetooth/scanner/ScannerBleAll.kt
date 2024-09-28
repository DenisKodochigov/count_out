package com.example.count_out.service.bluetooth.scanner

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.os.ParcelUuid
import com.example.count_out.entity.Const
import com.example.count_out.entity.SendToUI
import com.example.count_out.entity.bluetooth.BleStates
import com.example.count_out.permission.PermissionApp
import com.example.count_out.service.bluetooth.objectScanCallback
import com.example.count_out.service.bluetooth.objectScanCallbackF
import com.example.count_out.service.bluetooth.scanSettings
import com.example.count_out.ui.view_components.lg
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class ScannerBleAll(
    private val bluetoothAdapter: BluetoothAdapter,
    private val permissionApp: PermissionApp
){
    private lateinit var scanCallback: ScanCallback
    private val bleScanner by lazy { bluetoothAdapter.bluetoothLeScanner }

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

    @SuppressLint("MissingPermission")
    fun startScannerBLEDevices(bleStates: BleStates, sendToUI: MutableStateFlow<SendToUI>) {
        scanCallback = objectScanCallback(bleStates, sendToUI)
        permissionApp.checkBleScan{
            bleScanner.startScan( scanFilters(), scanSettings(0L), scanCallback) }
        sendToUI.update { send-> send.copy( scannedBle = true) }
    }

    @SuppressLint("MissingPermission")
    fun stopScannerBLEDevices(sendToUI: MutableStateFlow<SendToUI>){
        lg("Stop scanner")
        permissionApp.checkBleScan{ bleScanner.stopScan(scanCallback)}
        sendToUI.update { send-> send.copy( scannedBle = false) }
    }

    @SuppressLint("MissingPermission")
    fun startScannerBLEDevicesF(bleStates: BleStates, sendToUI: SendToUI) {
        scanCallback = objectScanCallbackF(bleStates, sendToUI)
        permissionApp.checkBleScan{
            bleScanner.startScan( scanFilters(), scanSettings(0L), scanCallback) }
//        sendToUI.update { send-> send.copy( scannedBle = true) }
        sendToUI.scannedBleF.value = true
    }

    @SuppressLint("MissingPermission")
    fun stopScannerBLEDevicesF(sendToUI: SendToUI){
        lg("Stop scanner")
        permissionApp.checkBleScan{ bleScanner.stopScan(scanCallback)}
//        sendToUI.update { send-> send.copy( scannedBle = false) }
        sendToUI.scannedBleF.value = false
    }
}