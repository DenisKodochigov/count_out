package com.example.count_out.service.bluetooth

import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import com.example.count_out.permission.PermissionApp
import com.example.count_out.ui.view_components.lg
import kotlinx.coroutines.flow.MutableStateFlow


fun addDevice(
    result: ScanResult,
    permissionApp: PermissionApp,
    devices: MutableStateFlow<List<BluetoothDevice>>): MutableList<BluetoothDevice>
{
    val listDevice: MutableList<BluetoothDevice> = devices.value.toMutableList()
    permissionApp.checkBleScan {
        listDevice.find { it.address == result.device.address } ?: listDevice.add(result.device)
    }
    return listDevice
}
fun scanSettings(reportDelay: Long): ScanSettings {
    return ScanSettings.Builder()
        .setScanMode(ScanSettings.SCAN_MODE_BALANCED)
        .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
        .setMatchMode(ScanSettings.MATCH_MODE_AGGRESSIVE)
        .setNumOfMatches(ScanSettings.MATCH_NUM_ONE_ADVERTISEMENT)
        .setReportDelay(reportDelay)
        .build()
}

fun objectScanCallback(
    permissionApp: PermissionApp,
    devices: MutableStateFlow<List<BluetoothDevice>>): ScanCallback = object: ScanCallback() {
    override fun onScanResult(callbackType: Int, result: ScanResult?) {
        super.onScanResult(callbackType, result)
        if (result != null) {
            lg("objectScanCallback ${result.device}")
            devices.value = addDevice(result, permissionApp, devices) }
    }
    override fun onBatchScanResults(results: MutableList<ScanResult>?) {
        super.onBatchScanResults(results)
        if (!results.isNullOrEmpty()) {
            results?.let{ resultsLoc->
                resultsLoc.forEach{ result->
                    lg("objectScanCallback ${result.device}")
                    devices.value = addDevice(result, permissionApp, devices)
                }
            }
        }
    }
    override fun onScanFailed(errorCode: Int) {
        lg("Error scan BLE device. $errorCode")
    }
}