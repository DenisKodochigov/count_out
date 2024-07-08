package com.example.count_out.service.bluetooth

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.util.Log
import com.example.count_out.entity.StateScanner
import com.example.count_out.entity.bluetooth.ValOutBleService
import com.example.count_out.ui.view_components.lg
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

fun scanSettings(reportDelay: Long): ScanSettings {
    return ScanSettings.Builder()
        .setScanMode(ScanSettings.SCAN_MODE_BALANCED)
        .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
        .setMatchMode(ScanSettings.MATCH_MODE_AGGRESSIVE)
        .setNumOfMatches(ScanSettings.MATCH_NUM_ONE_ADVERTISEMENT)
        .setReportDelay(reportDelay)
        .build()
}

fun objectScanCallback( valOut: ValOutBleService
): ScanCallback = object: ScanCallback()
{
    override fun onScanResult(callbackType: Int, result: ScanResult?) {
        super.onScanResult(callbackType, result)
        if (result != null) {
            valOut.listDevice.value.find { it.address == result.device.address }
                ?: valOut.listDevice.addApp(result.device)
            lg("objectScanCallback devices.value ${result.device.address}")
        }
        valOut.stateScanner.value = StateScanner.RUNNING
    }
    override fun onBatchScanResults(results: MutableList<ScanResult>?) {
        super.onBatchScanResults(results)
        if (!results.isNullOrEmpty()) {
            results.forEach{ result->
                valOut.listDevice.value.find { it.address == result.device.address }
                    ?: valOut.listDevice.addApp(result.device)
            }
        }
        valOut.stateScanner.value = StateScanner.RUNNING
    }
    override fun onScanFailed(errorCode: Int) {
        lg("Error scan BLE device. $errorCode")
        valOut.stateScanner.value = StateScanner.END
    }
}

fun BluetoothGatt.printCharacteristicsTable() {
    if (services.isEmpty()) {
        Log.i("printGattTable", "No service and characteristic available, call discoverServices() first?")
        return
    }
    services.forEach { service ->
        val characteristicsTable = service.characteristics.joinToString(
            separator = "\n|--",
            prefix = "|--"
        ) { it.uuid.toString() }
        Log.i("printGattTable", "\nService ${service.uuid}\nCharacteristics:\n$characteristicsTable"
        )
    }
}

fun BluetoothGattCharacteristic.isReadable(): Boolean =
    containsProperty(BluetoothGattCharacteristic.PROPERTY_READ)

fun BluetoothGattCharacteristic.containsProperty(property: Int) = properties and property != 0

fun BluetoothGattCharacteristic.isWritable(): Boolean =
    containsProperty(BluetoothGattCharacteristic.PROPERTY_WRITE)

fun BluetoothGattCharacteristic.isWritableWithoutResponse(): Boolean =
    containsProperty(BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE)

fun <T>MutableStateFlow<List<T>>.addApp(device: T) =
    update { this.value.toMutableList().apply { this.add(device) } }

