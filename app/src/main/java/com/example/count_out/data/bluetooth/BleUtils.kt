package com.example.count_out.data.bluetooth

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import com.example.count_out.data.bluetooth.modules.BleDevice
import com.example.count_out.data.bluetooth.modules.BleStates
import com.example.count_out.domain.router.DataFromBle
import com.example.count_out.entity.StateBleScanner
import com.example.count_out.ui.view_components.lg
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

fun objectScanCallback(bleStates: BleStates, dataFromBle: DataFromBle): ScanCallback = object: ScanCallback() {
    override fun onScanResult(callbackType: Int, result: ScanResult?) {
        super.onScanResult(callbackType, result)
        result?.device?.let { dev ->
            val fff = dataFromBle.foundDevices.value

            if (dataFromBle.foundDevices.value.find { it.address == dev.address } == null){
            dataFromBle.foundDevices.value = dataFromBle.foundDevices.value.addApp(BleDevice().fromBluetoothDevice(dev))
            }
        }
    }
    override fun onBatchScanResults(results: MutableList<ScanResult>?) {
        super.onBatchScanResults(results)
        if (!results.isNullOrEmpty()) {
            results.forEach{ result->
                if (dataFromBle.foundDevices.value.find { it.address == result.device.address } == null){
                dataFromBle.foundDevices.value =
                dataFromBle.foundDevices.value.addApp(BleDevice().fromBluetoothDevice(result.device))
                }
            }
        }
    }
    override fun onScanFailed(errorCode: Int) {
        lg("Error scan BLE device. $errorCode")
        dataFromBle.scannedBle.value = false
        bleStates.stateBleScanner = StateBleScanner.END
    }
}

fun BluetoothGatt.findCharacteristic(uuid: UUID): BluetoothGattCharacteristic?{
    if (services.isEmpty()) return null
    services.forEach { service ->
        service.characteristics.find { ch-> ch.uuid == uuid }.apply { if (this != null) return this }
    }
    return null
}

fun BluetoothGattCharacteristic.containsProperty(property: Int) = properties and property != 0
fun BluetoothGattCharacteristic.isReadable(): Boolean =
    containsProperty(BluetoothGattCharacteristic.PROPERTY_READ)
fun BluetoothGattCharacteristic.isWritable(): Boolean =
    containsProperty(BluetoothGattCharacteristic.PROPERTY_WRITE)
fun BluetoothGattCharacteristic.isNotify(): Boolean =
    containsProperty(BluetoothGattCharacteristic.PROPERTY_NOTIFY)
fun BluetoothGattCharacteristic.isIndicatable(): Boolean =
    containsProperty(BluetoothGattCharacteristic.PROPERTY_INDICATE)
//fun BluetoothGattCharacteristic.isWritableWithoutResponse(): Boolean =
//    containsProperty(BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE)

fun <T>List<T>.addApp(device: T): List<T> = this.toMutableList().apply { this.add(device) }

//fun BluetoothGatt.printCharacteristicsTable() {
//    if (services.isEmpty()) { return }
//    services.forEach { service ->
//        val characteristicsTable = service.characteristics.joinToString(separator = "\n|--", prefix = "|--") {
//            it.uuid.toString() + " Readable: " + it.isReadable() + " Writable: " + it.isWritable() +
//                    " Notify: " + it.isNotify() + " Indicate: " + it.isIndicatable()
//        }
//        lg("Service ${service.uuid}\nCharacteristics:\n$characteristicsTable")
//    }
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
//}
//fun <T>printHR(text: String, hr: MutableStateFlow<T>){
//    CoroutineScope(Dispatchers.Default).launch {
//        hr.collect{ hr->
//            when(hr){
//                is Int-> {lg( "printHR $text: $hr")}
//            }
//        }
//    }
//}
//fun generateHR(hr: MutableStateFlow<Int>){
//    CoroutineScope(Dispatchers.Default).launch {
//        var i = 0
//        while (i < 110){
//            delay(2000L)
//            if ( i > 100) i = 0 else i++
//            hr.value = i
////            lg("generateHR: ${hr.value}")
//        }
//    }
//}