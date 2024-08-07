package com.example.count_out.service.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import com.example.count_out.entity.StateScanner
import com.example.count_out.entity.bluetooth.BleDevice
import com.example.count_out.entity.bluetooth.BleStates
import com.example.count_out.entity.bluetooth.SendToUI
import com.example.count_out.ui.view_components.lg
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

fun scanSettings(reportDelay: Long): ScanSettings {
    return ScanSettings.Builder()
        .setScanMode(ScanSettings.SCAN_MODE_BALANCED)
        .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
        .setMatchMode(ScanSettings.MATCH_MODE_AGGRESSIVE)
        .setNumOfMatches(ScanSettings.MATCH_NUM_ONE_ADVERTISEMENT)
        .setReportDelay(reportDelay)
        .build()
}

fun objectScanCallback( bleStates: BleStates, sendToUI: SendToUI
): ScanCallback = object: ScanCallback()
{
    @SuppressLint("MissingPermission")
    override fun onScanResult(callbackType: Int, result: ScanResult?) {
        super.onScanResult(callbackType, result)
        result?.device?.let { dev ->
//            lg("objectScanCallback devices.value ${dev.address}")
            sendToUI.foundDevices.value.find { it.address == dev.address }
                ?: sendToUI.foundDevices.addApp(
                    BleDevice().fromBluetoothDevice(dev) )
        }
    }
    override fun onBatchScanResults(results: MutableList<ScanResult>?) {
        super.onBatchScanResults(results)
        if (!results.isNullOrEmpty()) {
            results.forEach{ result->
                sendToUI.foundDevices.value.find { it.address == result.device.address }
                    ?: sendToUI.foundDevices.addApp(BleDevice().fromBluetoothDevice(result.device))
            }
        }
    }
    override fun onScanFailed(errorCode: Int) {
        lg("Error scan BLE device. $errorCode")
        bleStates.stateScanner = StateScanner.END
    }
}

fun BluetoothGatt.findCharacteristic(uuid: UUID): BluetoothGattCharacteristic?{
    if (services.isEmpty()) return null
//    var charact: BluetoothGattCharacteristic? = null
    services.forEach { service ->
        service.characteristics.find { ch-> ch.uuid == uuid }.apply { if (this != null) return this }
    }
    return null
}

fun BluetoothGatt.printCharacteristicsTable() {
    if (services.isEmpty()) { return }
    services.forEach { service ->
        val characteristicsTable = service.characteristics.joinToString(separator = "\n|--", prefix = "|--") {
            it.uuid.toString() + " Readable: " + it.isReadable() + " Writable: " + it.isWritable() +
                    " Notify: " + it.isNotify() + " Indicate: " + it.isIndicatable()
        }
        lg("Service ${service.uuid}\nCharacteristics:\n$characteristicsTable")
    }
//    Service 00001800-0000-1000-8000-00805f9b34fb
//    Characteristics:
//    |--00002a00-0000-1000-8000-00805f9b34fb Readable: true Writable: true
//    |--00002a01-0000-1000-8000-00805f9b34fb Readable: true Writable: false
//    |--00002a04-0000-1000-8000-00805f9b34fb Readable: true Writable: false
//    |--00002aa6-0000-1000-8000-00805f9b34fb Readable: true Writable: false
//    2024-07-16 22:01:53.867 KDS                      D  printGattTable
//    Service 00001801-0000-1000-8000-00805f9b34fb
//    Characteristics:
//    |--
//    2024-07-16 22:01:53.869 KDS                      D  printGattTable
//    Service 0000180d-0000-1000-8000-00805f9b34fb
//    Characteristics:
//    |--00002a37-0000-1000-8000-00805f9b34fb Readable: false Writable: false
//    |--00002a38-0000-1000-8000-00805f9b34fb Readable: true Writable: false
//    2024-07-16 22:01:53.870 KDS                      D  printGattTable
//    Service 8ce5cc01-0a4d-11e9-ab14-d663bd873d93
//    Characteristics:
//    |--8ce5cc02-0a4d-11e9-ab14-d663bd873d93 Readable: false Writable: true
//    |--8ce5cc03-0a4d-11e9-ab14-d663bd873d93 Readable: false Writable: true
//    2024-07-16 22:01:53.870 KDS                      D  printGattTable
//    Service 0000180f-0000-1000-8000-00805f9b34fb
//    Characteristics:
//    |--00002a19-0000-1000-8000-00805f9b34fb Readable: true Writable: false
//    2024-07-16 22:01:53.872 KDS                      D  printGattTable
//    Service 0000180a-0000-1000-8000-00805f9b34fb
//    Characteristics:
//    |--00002a29-0000-1000-8000-00805f9b34fb Readable: true Writable: false
//    |--00002a24-0000-1000-8000-00805f9b34fb Readable: true Writable: false
//    |--00002a25-0000-1000-8000-00805f9b34fb Readable: true Writable: false
//    |--00002a27-0000-1000-8000-00805f9b34fb Readable: true Writable: false
//    |--00002a26-0000-1000-8000-00805f9b34fb Readable: true Writable: false
//    |--00002a28-0000-1000-8000-00805f9b34fb Readable: true Writable: false
}

fun BluetoothGattCharacteristic.isReadable(): Boolean =
    containsProperty(BluetoothGattCharacteristic.PROPERTY_READ)

fun BluetoothGattCharacteristic.containsProperty(property: Int) = properties and property != 0

fun BluetoothGattCharacteristic.isWritable(): Boolean =
    containsProperty(BluetoothGattCharacteristic.PROPERTY_WRITE)

fun BluetoothGattCharacteristic.isNotify(): Boolean =
    containsProperty(BluetoothGattCharacteristic.PROPERTY_NOTIFY)

fun BluetoothGattCharacteristic.isIndicatable(): Boolean =
    containsProperty(BluetoothGattCharacteristic.PROPERTY_INDICATE)
fun BluetoothGattCharacteristic.isWritableWithoutResponse(): Boolean =
    containsProperty(BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE)

fun <T>MutableStateFlow<List<T>>.addApp(device: T) =
    update { this.value.toMutableList().apply { this.add(device) } }

