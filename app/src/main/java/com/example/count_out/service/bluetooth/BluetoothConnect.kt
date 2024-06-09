package com.example.count_out.service.bluetooth

import android.Manifest.permission.BLUETOOTH_SCAN
import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothDevice.BOND_BONDED
import android.bluetooth.BluetoothDevice.BOND_BONDING
import android.bluetooth.BluetoothDevice.BOND_NONE
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGatt.GATT_FAILURE
import android.bluetooth.BluetoothGatt.GATT_SUCCESS
import android.bluetooth.BluetoothGattCallback
import android.content.Context
import com.example.count_out.entity.hciStatusFromValue
import com.example.count_out.permission.checkPermission
import com.example.count_out.ui.view_components.lg
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

class BluetoothConnect @Inject constructor( val context: Context
){
    private lateinit var scope: CoroutineScope
    private lateinit var gatt: BluetoothGatt
    private var deviceLocal: BluetoothDevice? = null

    private val bluetoothGattCallback = object : BluetoothGattCallback() {
        @SuppressLint("MissingPermission")
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            super.onConnectionStateChange(gatt, status, newState)
            lg("onConnectionStateChange status $status; newState: $newState")
            bluetoothGattCallbackConnectionStateChange(gatt, status, newState)
        }
        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            super.onServicesDiscovered(gatt, status)
            bluetoothGattCallbackServicesDiscovered(gatt, status)
        }
    }
    fun bluetoothGattCallbackConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int){
        lg("0")
        if (status == GATT_SUCCESS) {
            lg("1")
            when (newState) {
                BluetoothGatt.STATE_CONNECTED -> {
                    lg("2")
                    val bondState = checkPermission (context, BLUETOOTH_SCAN, requiredBuild = 31){
                        deviceLocal?.bondState ?: BOND_BONDING }
                    if (bondState == BOND_NONE || bondState == BOND_BONDED) {
                        lg("bond device SUCCESS")
                        gatt?.let { startDiscoverService(it) }
                    }
                    else { lg("waiting for bonding to complete") }
                }
                BluetoothGatt.STATE_DISCONNECTED -> {
                    lg("onConnectionStateChange BluetoothGatt.STATE_DISCONNECTED")
                    gattClose(gatt)
                }
                else -> { lg("onConnectionStateChange BluetoothGatt.GATT_SUCCESS") }
            }
        }
        else {
            lg("BluetoothGatt status: $status (${hciStatusFromValue(status)})")
            gattClose(gatt)
        }
    }
    fun bluetoothGattCallbackServicesDiscovered(gatt: BluetoothGatt?, status: Int){
        gatt?.let { gattV ->
            if (status == GATT_SUCCESS) {
                val services = gattV.services
                lg("Discovered ${services.size} services for $services")
            }
            else if (status != GATT_SUCCESS) {
                lg("Service discovery failed")
                if (status == GATT_FAILURE) {
                    checkPermission(context, BLUETOOTH_SCAN, requiredBuild = 31) { gattV.disconnect() }
                }
            }
            else lg("Service discovery failed")
        }
    }
    private fun startDiscoverService(gatt: BluetoothGatt){
        scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            checkPermission (context, BLUETOOTH_SCAN, requiredBuild = 31){
                if (!gatt.discoverServices()) { lg("discoverServices failed to start") } }
        }
    }
    fun connectDevice(device: BluetoothDevice){
        deviceLocal = device
        gatt = checkPermission (context, BLUETOOTH_SCAN, requiredBuild = 31){
            device.connectGatt(context, true, bluetoothGattCallback, BluetoothDevice.TRANSPORT_LE)
        } as BluetoothGatt
    }
    fun disconnectDevice(){
        checkPermission (context, BLUETOOTH_SCAN, requiredBuild = 31){ gatt.disconnect()}
    }
    fun clearServicesCache(): Boolean{
        var result = false
        try {
            val refreshMethod = gatt.javaClass.getMethod("refresh")
            result = refreshMethod.invoke(gatt) as Boolean
        } catch (e: Exception){
            lg("ERROR: Could not invoke refresh method: $e")
        }
        return result
    }
    private fun gattClose(gatt: BluetoothGatt?){
        onCancelDiscoverService()
        gatt?.let {checkPermission (context, BLUETOOTH_SCAN, requiredBuild = 31){ it.close()}}
    }
    private fun onCancelDiscoverService(){
        scope.cancel()
    }
}