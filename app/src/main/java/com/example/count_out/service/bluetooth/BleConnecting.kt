package com.example.count_out.service.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothDevice.BOND_BONDED
import android.bluetooth.BluetoothDevice.BOND_NONE
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGatt.GATT_FAILURE
import android.bluetooth.BluetoothGatt.GATT_SUCCESS
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.content.Context
import com.example.count_out.entity.Const.DELAY_CANCELED_COROUTINE
import com.example.count_out.entity.bluetooth.UUIDBle
import com.example.count_out.entity.bluetooth.ValInBleService
import com.example.count_out.entity.hciStatusFromValue
import com.example.count_out.permission.PermissionApp
import com.example.count_out.ui.view_components.lg
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


class BleConnecting @Inject constructor(
    val context: Context, private val permissionApp: PermissionApp,
) {
    private lateinit var jobDiscoverService: Job
    private lateinit var jobReadBleCharacteristic: Job
    private var gatt: BluetoothGatt? = null
    private var deviceConnect: BluetoothDevice? = null
    private val bleQueue = BleQueue()
    private val newStateLoc = MutableStateFlow(BluetoothGatt.STATE_DISCONNECTED)
    private val connectionStatus = MutableStateFlow(0)
    private val bluetoothGattCallback = object : BluetoothGattCallback() {
        @SuppressLint("MissingPermission")
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int)
        {
            super.onConnectionStateChange(gatt, status, newState)
            lg("onConnectionStateChange status $status; newState: $newState")
            bluetoothGattCallbackConnectionStateChange(gatt, status, newState)
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int)
        {
            super.onServicesDiscovered(gatt, status)
            bluetoothGattCallbackServicesDiscovered(gatt, status)
        }

        override fun onCharacteristicRead(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            value: ByteArray,
            status: Int,
        ){
            super.onCharacteristicRead(gatt, characteristic, value, status)
            bluetoothGattCallbackCharacteristicRead(status, value, characteristic)
        }
    }

    @SuppressLint("MissingPermission")
    fun bluetoothGattCallbackConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int,
    ) {
        connectionStatus.value = status
        newStateLoc.value = newState
        if (status == GATT_SUCCESS) {
            when (newState) {
                BluetoothGatt.STATE_DISCONNECTED -> {
                    lg("Connection state Bluetooth Gatt STATE_DISCONNECTED")
                    bluetoothGattCallbackGattClose(gatt)
                }
                else -> { lg("Connection state Bluetooth Gatt GATT_SUCCESS") }
            }
        } else {
            lg("BluetoothGatt status: $status (${hciStatusFromValue(status)})")
            bluetoothGattCallbackGattClose(gatt)
        }
    }

    @SuppressLint("MissingPermission")
    fun bluetoothGattCallbackServicesDiscovered(gatt: BluetoothGatt?, status: Int)
    {
        gatt?.let { gattV ->
            if (status == GATT_SUCCESS) {
                val services = gattV.services
                lg("Discovered ${services.size} services")
            } else {
                lg("Service discovery failed")
                if (status == GATT_FAILURE) permissionApp.checkBleScan { gattV.disconnect() }
            }
        }
    }

    fun bluetoothGattCallbackCharacteristicRead(
        status: Int,
        value: ByteArray,
        characteristic: BluetoothGattCharacteristic,
    ) {
        if (status != GATT_SUCCESS) {
            lg("ERROR: Read failed for characteristic: ${characteristic.uuid}, status $status")
            bleQueue.completedCommand();
            return;
        }
        // Characteristic has been read so processes it
        //...
        // We done, complete the command
        bleQueue.completedCommand();
    }

    @SuppressLint("MissingPermission")
    fun boundDevice(device: BluetoothDevice){
         if ( newStateLoc.value == BluetoothGatt.STATE_CONNECTED) {
            val bondState = permissionApp.checkBleScan { device.bondState }
            if (bondState == BOND_NONE || bondState == BOND_BONDED) {
                lg("Bond device SUCCESS")
                gatt?.let { startDiscoverService(it) }
            } else {
                lg("waiting for bonding to complete")
            }
        }
    }
    @SuppressLint("MissingPermission")
    private fun startDiscoverService(gatt: BluetoothGatt,
    ) {
        jobDiscoverService = CoroutineScope(Dispatchers.Default).launch {
            permissionApp.checkBleScan {
                if (gatt.discoverServices()) {
                    gatt.printCharacteristicsTable()
                } else {
                    lg("discoverServices failed to start")
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun connectDevice(valIn: ValInBleService) {
        valIn.device.value.device?.let { deviceIn->
            deviceConnect = deviceIn
            gatt = permissionApp.checkBleScan {
                deviceIn.connectGatt(context, true, bluetoothGattCallback, BluetoothDevice.TRANSPORT_LE)
            } as BluetoothGatt
        }
    }

    @SuppressLint("MissingPermission")
    fun disconnectDevice() {
        gatt?.let { permissionApp.checkBleScan { it.disconnect() } }
    }

    fun clearServicesCache(): Boolean {
        var result = false
        gatt?.let { gattV ->
            try {
                val refreshMethod = gattV.javaClass.getMethod("refresh")
                result = refreshMethod.invoke(gatt) as Boolean
            } catch (e: Exception) {
                lg("ERROR: Could not invoke refresh method: $e")
            }
        }
        return result
    }

    @SuppressLint("MissingPermission")
    private fun bluetoothGattCallbackGattClose(gatt: BluetoothGatt?) {
        runBlocking { onCancelDiscoverService() }
        gattCansel(gatt)
    }

    private suspend fun onCancelDiscoverService() {
        jobDiscoverService.cancel()
        while(jobReadBleCharacteristic.isCancelled){ delay(DELAY_CANCELED_COROUTINE)}
    }
    private suspend fun onCancelReadBleCharacteristic() {
        jobReadBleCharacteristic.cancel()
        while(jobReadBleCharacteristic.isCancelled) { delay(DELAY_CANCELED_COROUTINE)}
    }

    @SuppressLint("MissingPermission")
    private fun gattCansel(gatt: BluetoothGatt?) {
        gatt?.let { permissionApp.checkBleScan { it.close() } }
    }

    fun readParameterForBle(uuidBle: UUIDBle) {
        jobReadBleCharacteristic = CoroutineScope(Dispatchers.Default).launch {
            bleQueue.addCommandInQueue { readCharacteristicFun(permissionApp, gatt, uuidBle) }
        }.also {
            it.invokeOnCompletion { error ->
                when ( error ){
                    is CancellationException -> { lg("ERROR: $error") }
                    is RuntimeException -> { lg("ERROR: $error") }
                    else -> { lg("ERROR: $error") }
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun readCharacteristicFun(
        permissionApp: PermissionApp,
        gatt: BluetoothGatt?,
        uuidBle: UUIDBle,
    ): Boolean {
        var result = false
        gatt?.let { gt ->
            val characteristic =
                gt.getService(uuidBle.serviceUuid)?.getCharacteristic(uuidBle.charUuid)
            if (characteristic?.isReadable() == true) {
                permissionApp.checkBleScan { gt.readCharacteristic(characteristic) }
                result = true
                lg("reading characteristic ${characteristic.uuid}")
            } else {
                lg("ERROR: readCharacteristic failed for characteristic: ${characteristic?.uuid}")
            }
        } ?: lg("ERROR: Gatt is 'null', ignoring read request")
        return result
    }

    @SuppressLint("MissingPermission")
    fun bleConnectCancel() {
        runBlocking {
            onCancelReadBleCharacteristic()
            disconnectDevice()
            onCancelDiscoverService()
            gattCansel( gatt )
        }
    }
}