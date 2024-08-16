package com.example.count_out.service.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGatt.GATT_FAILURE
import android.bluetooth.BluetoothGatt.GATT_SUCCESS
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor.ENABLE_INDICATION_VALUE
import android.bluetooth.BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
import android.content.Context
import android.os.Build
import com.example.count_out.entity.Const.UUIDBle
import com.example.count_out.entity.ErrorBleService
import com.example.count_out.entity.StateService
import com.example.count_out.entity.bluetooth.BleConnection
import com.example.count_out.entity.bluetooth.BleStates
import com.example.count_out.entity.bluetooth.ReceiveFromUI
import com.example.count_out.entity.hciStatusFromValue
import com.example.count_out.permission.PermissionApp
import com.example.count_out.ui.view_components.lg
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.UUID
import javax.inject.Inject


class BleConnecting @Inject constructor(
    val context: Context, private val permissionApp: PermissionApp,
) {
//    private val bleQueue = BleQueue()
    private var connection = BleConnection()
    val heartRate: MutableStateFlow<Int> = MutableStateFlow(0)
    private val UUID_HEART_RATE_MEASUREMENT = UUID.fromString(UUIDBle.HEART_RATE_MEASUREMENT)
    private val UUID_CLIENT_CHARACTERISTIC_CONFIG = UUID.fromString(UUIDBle.CLIENT_CHARACTERISTIC_CONFIG)

/*################################################################################################ */
    fun connectDevice(bleStates: BleStates, receiveFromUI: ReceiveFromUI,){
//        generateHR(heartRate)
        receiveFromUI.currentConnection?.let {
            connection = it
            connectingGatt( bleStates )
        }
    }

    @SuppressLint("MissingPermission")
    fun connectingGatt(bleStates: BleStates): Boolean{
        var result = false
        if (bleStates.stateService == StateService.GET_REMOTE_DEVICE) {
            connection.device?.let { dev->
                if ( dev.connectGatt(
                        context, true, bluetoothGattCallback, BluetoothDevice.TRANSPORT_LE) != null)
                {
                    result = true
                    bleStates.stateService = StateService.CONNECT_GAT
                }
                else bleStates.error = ErrorBleService.NOT_CONNECT_GATT
            }
        }
        return result
    }

    private val bluetoothGattCallback = object: BluetoothGattCallback() {
        @SuppressLint("MissingPermission")
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            super.onConnectionStateChange(gatt, status, newState)
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
        ){
            super.onCharacteristicRead(gatt, characteristic, value, status)
            bluetoothGattCallbackCharacteristicRead(status, value, characteristic)
        }

        override fun onCharacteristicChanged(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            value: ByteArray
        ) {
            super.onCharacteristicChanged(gatt, characteristic, value)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bluetoothGattCallbackCharacteristicChanged(value, characteristic) }
        }
        override fun onCharacteristicChanged(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
        ) {
            super.onCharacteristicChanged(gatt, characteristic)
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                bluetoothGattCallbackCharacteristicChanged(ByteArray(0), characteristic)}
        }
    }

    @SuppressLint("MissingPermission")
    fun bluetoothGattCallbackConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int, ){
        connection.gattStatus.value = status
        connection.newState.value = newState
        if (status == GATT_SUCCESS) {
            connection.gatt = gatt
            when (newState) {
                BluetoothGatt.STATE_CONNECTED -> { gatt?.discoverServices() }
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
    fun bluetoothGattCallbackServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
        connection.gattStatus.value = status
        if (status == GATT_SUCCESS) {
            gatt?.let {
                it.printCharacteristicsTable()
                setCharacteristicNotification(it, UUID_HEART_RATE_MEASUREMENT, true)
            }
        } else {
            lg("Service discovery failed")
            connection.error.value = ErrorBleService.DISCOVER_SERVICE
            if (status == GATT_FAILURE) disconnectDevice(gatt)
        }
    }

    fun bluetoothGattCallbackCharacteristicRead(
        status: Int, value: ByteArray, characteristic: BluetoothGattCharacteristic, ) {
        connection.gattStatus.value = status
        if (status != GATT_SUCCESS) receiveValue(value, characteristic)
        else lg("ERROR: Read failed for characteristic: ${characteristic.uuid}, status $status")
    }

    fun bluetoothGattCallbackCharacteristicChanged(
        value: ByteArray, characteristic: BluetoothGattCharacteristic,
    ) {
        receiveValue(value, characteristic)
    }

    private fun receiveValue(value: ByteArray, characteristic: BluetoothGattCharacteristic){
        when (characteristic.uuid) {
            UUID_HEART_RATE_MEASUREMENT -> { heartRate.value = heartRateSDK(value, characteristic) }
            else -> { heartRateSDK(value, characteristic) }
        }
    }
    private fun heartRateSDK(value: ByteArray, characteristic: BluetoothGattCharacteristic): Int{
        val heartRate = if ( Build.VERSION.SDK_INT >= 33 ) value[1].dec().toInt()
        else characteristic.value[1].dec().toInt()
        return heartRate
    }

    fun clearServicesCache(): Boolean {
        var result = false
        connection.gatt?.let { gattL ->
            try {
                val refreshMethod = gattL.javaClass.getMethod("refresh")
                result = refreshMethod.invoke(gattL) as Boolean
            } catch (e: Exception) {
                lg("ERROR: Could not invoke refresh method: $e")
            }
        }
        return result
    }

    @SuppressLint("MissingPermission")
    private fun bluetoothGattCallbackGattClose(gatt: BluetoothGatt?) {
        disconnectDevice(gatt)
    }

    @SuppressLint("MissingPermission")
    fun disconnectDevice(gatt: BluetoothGatt? = connection.gatt) {
        gatt?.let { permissionApp.checkBleScan {
            it.disconnect()
            it.close()
        } }
    }

    @SuppressLint("MissingPermission")
    fun setCharacteristicNotification(gatt: BluetoothGatt, uuid: UUID, enabled: Boolean) {
//        lg("setCharacteristicNotification ")
        gatt.findCharacteristic(uuid)?.let{ characteristic->
//            lg("setCharacteristicNotification $characteristic")
            gatt.setCharacteristicNotification(characteristic, enabled)
            val descriptor = characteristic.getDescriptor(UUID_CLIENT_CHARACTERISTIC_CONFIG)
            val descriptorValue = if (characteristic.isNotify()) { ENABLE_NOTIFICATION_VALUE
            } else { ENABLE_INDICATION_VALUE }
//            lg("setCharacteristicNotification ${Build.VERSION.SDK_INT} descriptor: $descriptor")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                gatt.writeDescriptor(descriptor, descriptorValue)
            } else {
                descriptor.value = descriptorValue
                gatt.writeDescriptor(descriptor)
            }
        }
    }

}