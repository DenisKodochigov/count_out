package com.count_out.device.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.os.ParcelUuid
import android.util.Log
import com.count_out.device.bluetooth.models.BleConnectionImpl
import com.count_out.device.bluetooth.models.Const
import com.count_out.device.bluetooth.models.ResultBle
import com.count_out.device.bluetooth.models.ThrowableBle
import com.count_out.device.permission.PermissionApp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BleScanner @Inject constructor(
    val context: Context,
    private val bluetoothAdapter: BluetoothAdapter,
    private val permissionApp: PermissionApp
) {
    val dataFromBle: MutableStateFlow<ResultBle> = MutableStateFlow(ResultBle.Nothing)
    private var scanCallback: ScanCallback = objectScanCallback(dataFromBle)
    private val bluetoothScanner by lazy { bluetoothAdapter.bluetoothLeScanner }

    private fun settings(): ScanSettings {
        return ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_BALANCED)
            .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
            .setMatchMode(ScanSettings.MATCH_MODE_AGGRESSIVE)
            .setNumOfMatches(ScanSettings.MATCH_NUM_ONE_ADVERTISEMENT)
            .setReportDelay(0L)
            .build()
    }
    private fun filters(): List<ScanFilter> {
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
    fun startScanner(): Flow<ResultBle> {
        Log.d("KDS", "startScanner")
        bluetoothScanner.startScan(filters(), settings(), scanCallback)
        return dataFromBle
    }

    @SuppressLint("MissingPermission")
    fun stopScanner(): Flow<ResultBle>{
        Log.d("KDS", "stopScanner")
        bluetoothScanner.stopScan(scanCallback)
        return dataFromBle
    }
    private fun objectScanCallback(dataFromBle: MutableStateFlow<ResultBle>): ScanCallback = object: ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            super.onScanResult(callbackType, result)
            result?.device?.let { dev ->
//                Log.d("KDS", "onScanResult $dev")
                dataFromBle.value = ResultBle.Device(
                    BleConnectionImpl().fromBluetoothDevice(dev)) }
        }
        override fun onBatchScanResults(results: MutableList<ScanResult>?) {
            super.onBatchScanResults(results)
            if (!results.isNullOrEmpty()) {
                results.forEach{ result->
                    Log.d("KDS", "onBatchScanResults ${result.device}")
                    dataFromBle.value = ResultBle
                        .Device( BleConnectionImpl().fromBluetoothDevice(result.device)) }
            }
        }
        override fun onScanFailed(errorCode: Int) {
            dataFromBle.value = ResultBle.Error(throwable = ThrowableBle.Scanning())
        }
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