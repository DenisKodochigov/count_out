package com.example.count_out.service.bluetooth

import android.Manifest.permission.BLUETOOTH_SCAN
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothDevice.DEVICE_TYPE_UNKNOWN
import android.bluetooth.BluetoothDevice.TRANSPORT_LE
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGatt.GATT_SUCCESS
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
import com.example.count_out.entity.Const.serviceUUIDs
import com.example.count_out.permission.checkPermission
import com.example.count_out.ui.view_components.lg
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class BluetoothApp @Inject constructor(val context: Context) {
    private lateinit var bluetoothManager: BluetoothManager
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var scope: CoroutineScope
    private lateinit var gatt: BluetoothGatt
    private val devices: MutableStateFlow<List<BluetoothDevice>> = MutableStateFlow(emptyList())

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
        override fun onScanFailed(errorCode: Int) {
            lg("Error scan BLE device. $errorCode")
        }
    }
    private val bluetoothGattCallback = object : BluetoothGattCallback() {
        @SuppressLint("MissingPermission")
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            super.onConnectionStateChange(gatt, status, newState)
            lg("onConnectionStateChange status $status; newState: $newState")
            if(status == GATT_SUCCESS) {
                if (newState == BluetoothGatt.STATE_CONNECTED) {
                    lg("onConnectionStateChange NOT BluetoothGatt.GATT_SUCCESS")
                    scope = CoroutineScope(Dispatchers.Default)
                    scope.launch { gatt?.discoverServices()}
                }
                else if (newState == BluetoothGatt.STATE_DISCONNECTED) {
                    lg("onConnectionStateChange BluetoothGatt.STATE_DISCONNECTED")
                    gatt?.close()
                }
                else { lg("onConnectionStateChange BluetoothGatt.GATT_SUCCESS") }
            }
            // Произошла ошибка... разбираемся, что случилось!
            else { gatt?.close() }
        }
        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            super.onServicesDiscovered(gatt, status)
            if (status != BluetoothGatt.GATT_SUCCESS) lg("onServicesDiscovered NOT BluetoothGatt.GATT_SUCCESS")
            else lg("onServicesDiscovered BluetoothGatt.GATT_SUCCESS")
        }
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
    fun addDevice(result: ScanResult,  devices: MutableStateFlow<List<BluetoothDevice>>): MutableList<BluetoothDevice>{
        val listDevice: MutableList<BluetoothDevice> = devices.value.toMutableList()
        checkPermission (context, BLUETOOTH_SCAN, 31) {
            listDevice.find { it.address == result.device.address } ?: listDevice.add(result.device)}
        return listDevice
    }

    private fun scanFilters(): List<ScanFilter> {
        val filters = mutableListOf<ScanFilter>()
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
        checkPermission (context, BLUETOOTH_SCAN, requiredBuild = 31)
        { bluetoothAdapter.bluetoothLeScanner.startScan(scanFilters(), scanSettings(), scanCallback) }
    }
    fun connectDevice(device: BluetoothDevice){
        gatt = checkPermission (context, BLUETOOTH_SCAN, requiredBuild = 31){
            device.connectGatt(context, true, bluetoothGattCallback, TRANSPORT_LE)
        } as BluetoothGatt
    }

    fun stopScanner(){
        lg("Stop scanner")
        checkPermission (context, BLUETOOTH_SCAN, requiredBuild = 31){
            bluetoothAdapter.bluetoothLeScanner.stopScan(scanCallback)}
    }

    fun deviceCached(device: BluetoothDevice): Boolean {
        return checkPermission (context, BLUETOOTH_SCAN, requiredBuild = 31){
            device.type == DEVICE_TYPE_UNKNOWN
        } as Boolean
    }
    fun onCancel(){
        stopScanner()
        scope.cancel()
    }

}

