package com.example.count_out.service.bluetooth

import android.Manifest.permission.BLUETOOTH_SCAN
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothDevice.TRANSPORT_LE
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.content.Intent
import android.os.ParcelUuid
import androidx.core.app.ActivityCompat.startActivityForResult
import com.example.count_out.MainActivity
import com.example.count_out.permission.checkPermission
import com.example.count_out.ui.view_components.lg
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BluetoothApp @Inject constructor(val context: Context) {
    private lateinit var bluetoothManager: BluetoothManager
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private val devices: MutableStateFlow<List<BluetoothDevice>> = MutableStateFlow(emptyList())

    private val leScanCallback =
        BluetoothAdapter.LeScanCallback { device, rssi, scanRecord ->
            // Handle the discovered device here
        }
    private val scanCallback = object:ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            super.onScanResult(callbackType, result)
            if (result != null) {
                devices.value = addDevice(result, devices)
                lg(" devices: ${result}")
            }
        }

        override fun onBatchScanResults(results: MutableList<ScanResult>?) {
            super.onBatchScanResults(results)
        }
    }

    fun addDevice(result: ScanResult,  devices: MutableStateFlow<List<BluetoothDevice>>): MutableList<BluetoothDevice>{
        val listDevice: MutableList<BluetoothDevice> = devices.value.toMutableList()
        checkPermission (context, BLUETOOTH_SCAN, 30) {
            listDevice.find { it.address == result.device.address } ?: listDevice.add(result.device)}
//                BluetoothDev(name = result.device.name ?: "", address = result.device.address)) }
        return listDevice
    }
    fun init(activity: MainActivity){
        bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter
        if ( !bluetoothAdapter.isEnabled) {
            /*
            Next, you need to ensure that Bluetooth is enabled. Call isEnabled() to check whether
            Bluetooth is currently enabled. If this method returns false, then Bluetooth is disabled.
            To request that Bluetooth be enabled, call startActivityForResult(), passing in an
            ACTION_REQUEST_ENABLE intent action. This call issues a request to enable Bluetooth
            through the system settings (without stopping your app).
             */
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult( activity, enableBtIntent, 1, null)
        }
    }
    private fun scanFilters(): List<ScanFilter> {
        val filters = mutableListOf<ScanFilter>()
        val serviceUUIDs = listOf(
            UUID.fromString("00001809-0000-0000-0000-000000000000"),
            UUID.fromString("00001810-0000-1000-8000-000000000000"),
            UUID.fromString("0000180D-0000-0000-0000-000000000000"),
            UUID.fromString("0000180E-0000-0000-0000-000000000000"),
            UUID.fromString("00001812-0000-0000-0000-000000000000"),
            UUID.fromString("00001814-0000-0000-0000-000000000000"),
            UUID.fromString("00001816-0000-0000-0000-000000000000"),
            UUID.fromString("00001818-0000-0000-0000-000000000000"),
            UUID.fromString("00001819-0000-0000-0000-000000000000"),
            UUID.fromString("00001822-0000-0000-0000-000000000000"),
            UUID.fromString("00001826-0000-0000-0000-000000000000"),
            UUID.fromString("0000183E-0000-0000-0000-000000000000"),
            UUID.fromString("00001840-0000-0000-0000-000000000000"),
            )
        for(serviceUUID in serviceUUIDs){
            val filter = ScanFilter.Builder().setServiceUuid(ParcelUuid(serviceUUID),
                ParcelUuid(UUID.fromString("11111111-0000-0000-0000-000000000000"))).build()
//            val filter = ScanFilter.Builder().setDeviceName("name").build()
//            val filter = ScanFilter.Builder().setDeviceAddress("mac address").build()
            filters.add(filter)
        }
        return filters
    }
    private fun scanSettings(): ScanSettings {
        return ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_BALANCED)
            .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
            .setMatchMode(ScanSettings.MATCH_MODE_AGGRESSIVE)
            .setNumOfMatches(ScanSettings.MATCH_NUM_ONE_ADVERTISEMENT)
            .setReportDelay(0L)
            .build()
    }
    fun getDevices() = devices

    fun queryPairedDevices() {
        checkPermission (context, BLUETOOTH_SCAN, 30)
        { bluetoothAdapter.bluetoothLeScanner.startScan(scanFilters(), scanSettings(), scanCallback) }
    }
    fun connectDevice(device: BluetoothDevice){
        checkPermission (context, BLUETOOTH_SCAN, 30){
            val bluetoothGattCallback = object : BluetoothGattCallback() {
                @SuppressLint("MissingPermission")
                override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
                    super.onConnectionStateChange(gatt, status, newState)
                    lg("onConnectionStateChange: ")
                    if (status != BluetoothGatt.GATT_SUCCESS) {
                        lg("onConnectionStateChange NOT BluetoothGatt.GATT_SUCCESS")
                        gatt?.discoverServices()
                    }
                    else {
                        lg("onConnectionStateChange BluetoothGatt.GATT_SUCCESS")
                        gatt?.close()
                    }
                }

                override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
                    super.onServicesDiscovered(gatt, status)
                    if (status != BluetoothGatt.GATT_SUCCESS) lg("onServicesDiscovered NOT BluetoothGatt.GATT_SUCCESS")
                    else lg("onServicesDiscovered BluetoothGatt.GATT_SUCCESS")
                }
            }

            val gatt: BluetoothGatt = device.connectGatt(context, false, bluetoothGattCallback, TRANSPORT_LE)
        }
    }
    fun stopScanner(){
        lg("Stop scanner")
        checkPermission (context, BLUETOOTH_SCAN, 30){
            bluetoothAdapter.bluetoothLeScanner.stopScan(scanCallback)}
    }
    fun discoverDevices(){

    }

}

