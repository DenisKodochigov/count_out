package com.example.count_out.service.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothDevice.BOND_BONDED
import android.bluetooth.BluetoothDevice.BOND_BONDING
import android.bluetooth.BluetoothDevice.BOND_NONE
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGatt.GATT_FAILURE
import android.bluetooth.BluetoothGatt.GATT_SUCCESS
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.content.Context
import com.example.count_out.entity.UUIDBle
import com.example.count_out.entity.hciStatusFromValue
import com.example.count_out.permission.PermissionApp
import com.example.count_out.ui.view_components.lg
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject


class BleConnect @Inject constructor(
    val context: Context, private val permissionApp: PermissionApp,
){
    private lateinit var scope: CoroutineScope
    private var gatt: BluetoothGatt? = null
    private var deviceConnect: BluetoothDevice? = null
    private val bleQueueCommand = BleQueueCommand()


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

        override fun onCharacteristicRead(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            value: ByteArray,
            status: Int,
        ) {
            super.onCharacteristicRead(gatt, characteristic, value, status)
            if (status != GATT_SUCCESS) {
                lg( "ERROR: Read failed for characteristic: ${characteristic.uuid}, status $status")
                bleQueueCommand.completedCommand(gatt);
                return;
            }
            // Characteristic has been read so processes it
            //...
            // We done, complete the command
            bleQueueCommand.completedCommand(gatt);
        }
    }

    @SuppressLint("MissingPermission")
    fun bluetoothGattCallbackConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int){
        if (status == GATT_SUCCESS) {
            when (newState) {
                BluetoothGatt.STATE_CONNECTED -> {
                    val bondState = permissionApp.checkBleScan { deviceConnect?.bondState ?: BOND_BONDING }
                    if (bondState == BOND_NONE || bondState == BOND_BONDED) {
                        lg("Bond device SUCCESS")
                        gatt?.let { startDiscoverService(it) }
                    }
                    else { lg("waiting for bonding to complete") }
                }
                BluetoothGatt.STATE_DISCONNECTED -> {
                    lg("Connection state Bluetooth Gatt STATE_DISCONNECTED")
                    gattClose(gatt)
                }
                else -> { lg("Connection state Bluetooth Gatt GATT_SUCCESS") }
            }
        }
        else {
            lg("BluetoothGatt status: $status (${hciStatusFromValue(status)})")
            gattClose(gatt)
        }
    }

    @SuppressLint("MissingPermission")
    fun bluetoothGattCallbackServicesDiscovered(gatt: BluetoothGatt?, status: Int){
        gatt?.let { gattV ->
            if (status == GATT_SUCCESS) {
                val services = gattV.services
                lg("Discovered ${services.size} services")
            }
            else {
                lg("Service discovery failed")
                if (status == GATT_FAILURE) permissionApp.checkBleScan { gattV.disconnect() }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun startDiscoverService(gatt: BluetoothGatt){
        scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            permissionApp.checkBleScan {
                if (gatt.discoverServices()) {
                    gatt.printCharacteristicsTable()
                } else { lg("discoverServices failed to start") } }
        }
    }

    @SuppressLint("MissingPermission")
    fun connectDevice(device: BluetoothDevice){
        deviceConnect = device
        gatt = permissionApp.checkBleScan {
            device.connectGatt(context, true, bluetoothGattCallback, BluetoothDevice.TRANSPORT_LE)
        } as BluetoothGatt
    }

    @SuppressLint("MissingPermission")
    fun disconnectDevice(){
        gatt?.let { permissionApp.checkBleScan { it.disconnect()} }
    }

    fun clearServicesCache(): Boolean{
        var result = false
        gatt?.let{ gattV->
            try {
                val refreshMethod = gattV.javaClass.getMethod("refresh")
                result = refreshMethod.invoke(gatt) as Boolean
            } catch (e: Exception){
                lg("ERROR: Could not invoke refresh method: $e")
            }
        }
        return result
    }

    @SuppressLint("MissingPermission")
    private fun gattClose(gatt: BluetoothGatt?){
        onCancelDiscoverService()
        gatt?.let {permissionApp.checkBleScan { it.close()}}
    }

    private fun onCancelDiscoverService(){
        scope.cancel()
    }

    fun readParameterForBle(uuidBle: UUIDBle){
        bleQueueCommand.readCharacteristic( gatt, permissionApp, uuidBle)
    }
}