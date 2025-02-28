package com.count_out.device.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.os.ParcelUuid
import com.count_out.data.models.RunningState
import com.count_out.data.router.models.DataFromBle
import com.count_out.device.bluetooth.models.BleStates
import com.count_out.device.bluetooth.models.Const
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BleScanner @Inject constructor(
    val context: Context,
    private val bluetoothAdapter: BluetoothAdapter,
//    private val permissionApp: PermissionApp
) {
//    private val timer = TimerMy()
    private lateinit var scanCallback: ScanCallback
    private val bleScanner by lazy { bluetoothAdapter.bluetoothLeScanner }
    private val timeScanning = 120

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

    @SuppressLint("MissingPermission", "SuspiciousIndentation")
    fun startScannerBLEDevices(dataFromBle: DataFromBle, bleStates: BleStates) {
        CoroutineScope(Dispatchers.Default).launch {
            scanCallback = objectScanCallback(bleStates, dataFromBle)
//            permissionApp.checkBleScan{
                bleScanner.startScan( scanFilters(), scanSettings(0L), scanCallback)
//            }
            dataFromBle.scannedBle.value = true
//            Delay().run(
//                delay = timeScanning * 1000L,
//                bleStates.stateBleScanner
//            )
            bleStates.stateBleScanner.value = RunningState.Stopped
            stopScanner(dataFromBle)
//            timer.start(
//                sec = timeScanning,
//                endCommand = {
//                    bleStates.stateBleScanner = StateBleScanner.END
//                    stopScanner(dataFromBle)
//                }
//            )
        }
    }
    fun stopScannerBLEDevices(dataFromBle: DataFromBle) {
//        timer.cancel()
        stopScanner(dataFromBle)
    }
    @SuppressLint("MissingPermission")
    fun stopScanner(dataFromBle: DataFromBle){
//        lg("BleScanner. Stop scanner")
//        permissionApp.checkBleScan{
            bleScanner.stopScan(scanCallback)
//        }
        dataFromBle.scannedBle.value = false
    }
    private fun objectScanCallback(bleStates: BleStates, dataFromBle: DataFromBle): ScanCallback = object: ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            super.onScanResult(callbackType, result)
            result?.device?.let { dev ->
                val fff = dataFromBle.foundDevices.value

//                if (dataFromBle.foundDevices.value.find { it.address == dev.address } == null){
//                    dataFromBle.foundDevices.value = dataFromBle.foundDevices.value.addApp(
//                        BleDeviceImpl().fromBluetoothDevice(dev))
//                }
            }
        }
        override fun onBatchScanResults(results: MutableList<ScanResult>?) {
            super.onBatchScanResults(results)
            if (!results.isNullOrEmpty()) {
                results.forEach{ result->
//                    if (dataFromBle.foundDevices.value.find { it.address == result.device.address } == null){
//                        dataFromBle.foundDevices.value =
//                            dataFromBle.foundDevices.value.addApp(BleDeviceImpl().fromBluetoothDevice(result.device))
//                    }
                }
            }
        }
        override fun onScanFailed(errorCode: Int) {
//            lg("Error scan BLE device. $errorCode")
            dataFromBle.scannedBle.value = false
            bleStates.stateBleScanner.value = RunningState.Stopped
        }
    }
    private fun scanSettings(reportDelay: Long): ScanSettings {
        return ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_BALANCED)
            .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
            .setMatchMode(ScanSettings.MATCH_MODE_AGGRESSIVE)
            .setNumOfMatches(ScanSettings.MATCH_NUM_ONE_ADVERTISEMENT)
            .setReportDelay(reportDelay)
            .build()
    }

}

//fun BluetoothGattCharacteristic.isWritableWithoutResponse(): Boolean =
//    containsProperty(BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE)


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