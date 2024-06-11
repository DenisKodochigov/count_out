package com.example.count_out.service.bluetooth

import android.Manifest.permission.BLUETOOTH_SCAN
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.os.ParcelUuid
import com.example.count_out.entity.BluetoothDeviceApp
import com.example.count_out.entity.Const
import com.example.count_out.permission.checkPermission
import com.example.count_out.ui.view_components.lg
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BluetoothScanner @Inject constructor(
    val context: Context, private val bluetoothAdapter: BluetoothAdapter
){
    private val devices: MutableStateFlow<List<BluetoothDevice>> = MutableStateFlow(emptyList())
    private val devicesByMac: MutableStateFlow<BluetoothDeviceApp> = MutableStateFlow(BluetoothDeviceApp())
     private fun scanSettings(): ScanSettings {
        return ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_BALANCED)
            .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
            .setMatchMode(ScanSettings.MATCH_MODE_AGGRESSIVE)
            .setNumOfMatches(ScanSettings.MATCH_NUM_ONE_ADVERTISEMENT)
            .setReportDelay(0L)
            .build()
    }

    /** Scan device by MAC address*/
    private val scanCallbackByMac = object: ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            super.onScanResult(callbackType, result)
            if (result != null) { devicesByMac.value.device =  result.device }
        }
        override fun onBatchScanResults(results: MutableList<ScanResult>?) {
            super.onBatchScanResults(results)
        }
        override fun onScanFailed(errorCode: Int) {
            lg("Error scan BLE device. $errorCode")
        }
    }
    fun findBleDeviceByMac(mac: String){
        devices.value = emptyList()
        val scanFilterMac = listOf(ScanFilter.Builder().setDeviceAddress(mac).build())
        checkPermission (context, BLUETOOTH_SCAN, requiredBuild = 31)
        { bluetoothAdapter.bluetoothLeScanner.startScan( scanFilterMac, scanSettings(), scanCallbackByMac) }
    }
    fun stopScannerBLEDevicesByMac(){
        lg("Stop scanner")
        checkPermission (context, BLUETOOTH_SCAN, requiredBuild = 31){
            bluetoothAdapter.bluetoothLeScanner.stopScan(scanCallbackByMac)}
    }
    fun getDeviceBYMac() = devicesByMac

    /** Scan all device*/
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
    private val scanCallback = object: ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            super.onScanResult(callbackType, result)
            if (result != null) {
                devices.value = addDevice(result, devices)
//                lg(" devices: ${result}")
            }
        }

        override fun onBatchScanResults(results: MutableList<ScanResult>?) {
            super.onBatchScanResults(results)
        }
        override fun onScanFailed(errorCode: Int) {
            lg("Error scan BLE device. $errorCode")
        }
    }
    fun startScannerBLEDevices() {
        checkPermission (context, BLUETOOTH_SCAN, requiredBuild = 31)
        { bluetoothAdapter.bluetoothLeScanner.startScan(scanFilters(), scanSettings(), scanCallback) }
    }
    fun stopScannerBLEDevices(){
        lg("Stop scanner")
        checkPermission (context, BLUETOOTH_SCAN, requiredBuild = 31){
            bluetoothAdapter.bluetoothLeScanner.stopScan(scanCallback)}
    }
    fun getDevices() = devices

    fun addDevice(result: ScanResult, devices: MutableStateFlow<List<BluetoothDevice>>): MutableList<BluetoothDevice>{
        val listDevice: MutableList<BluetoothDevice> = devices.value.toMutableList()
        checkPermission (context, BLUETOOTH_SCAN, 31) {
            listDevice.find { it.address == result.device.address } ?: listDevice.add(result.device)}
        return listDevice
    }
}
