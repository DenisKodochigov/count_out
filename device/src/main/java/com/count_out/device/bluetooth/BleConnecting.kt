package com.count_out.device.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice.TRANSPORT_LE
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGatt.GATT_FAILURE
import android.bluetooth.BluetoothGatt.GATT_SUCCESS
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor.ENABLE_INDICATION_VALUE
import android.bluetooth.BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
import android.content.Context
import android.os.Build
import android.util.Log
import com.count_out.device.bluetooth.models.BleConnectionImpl
import com.count_out.device.bluetooth.models.ResultBle
import com.count_out.device.bluetooth.models.ThrowableBle
import com.count_out.domain.entity.enums.StateBleConnecting
import com.count_out.domain.entity.enums.UUIDBle
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import java.util.UUID

class BleConnecting @Inject constructor(val context: Context) {
    val connection: MutableStateFlow<BleConnectionImpl> = MutableStateFlow( BleConnectionImpl())
    val heartRate: MutableStateFlow<Int> = MutableStateFlow(0)
    private val uuidHeartRateMeasurement = UUID.fromString(UUIDBle.HEART_RATE_MEASUREMENT)
    private val uuidClientCharacteristicConfig = UUID.fromString(UUIDBle.CLIENT_CHARACTERISTIC_CONFIG)

    fun connectDevice( bleConnection: BleConnectionImpl): Flow<ResultBle> {
        Log.d("KDS", "BleConnecting.connectDevice ${connection.value.newState}")
        connection.value = bleConnection
        return connectingGatt()
//            if (bleStates.error == ErrorBleService.NOT_CONNECT_GATT)  connectingGatt( bleStates )
//            dataForBle.currentConnection?.let {
//            connection = BleConnectionImpl(it)
//            connectingGatt( bleStates )
//            if (bleStates.error == ErrorBleService.NOT_CONNECT_GATT)  connectingGatt( bleStates )
//        }
    }
    @SuppressLint("MissingPermission")
    fun connectingGatt(): Flow<ResultBle>{
        return flow { emit(
            connection.value.device?.let { dev->
                if ( dev.connectGatt(context, true, bluetoothGattCallback, TRANSPORT_LE) != null) {
                    ResultBle.StateConnectingBle(StateBleConnecting.CONNECT_GAT)
                } else ResultBle.Error(ThrowableBle.NotConnectingGatt())
            } ?: ResultBle.Error(ThrowableBle.NoDevice())
        ) }
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
        @Deprecated("Deprecated in Java")
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
    fun bluetoothGattCallbackConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int){
        connection.value = connection.value.copy(gattStatus = status, newState = newState)
        if (status == GATT_SUCCESS && gatt != null) {
            connection.value = connection.value.copy(gatt = gatt)
            when (newState) {
                BluetoothGatt.STATE_CONNECTED -> { gatt.discoverServices() }
                BluetoothGatt.STATE_DISCONNECTED -> { disconnectDevice(gatt) }
            }
        } else { disconnectDevice(gatt) }
    }
    @SuppressLint("MissingPermission")
    fun bluetoothGattCallbackServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
        connection.value = connection.value.copy(gattStatus = status)
        if (status == GATT_SUCCESS && gatt != null) {
            setCharacteristicNotification(gatt, uuidHeartRateMeasurement, true)
        } else if (status == GATT_FAILURE) disconnectDevice(gatt)
    }
    fun bluetoothGattCallbackCharacteristicRead(
        status: Int, value: ByteArray, characteristic: BluetoothGattCharacteristic, ) {
        connection.value = connection.value.copy(gattStatus = status)
        if (status == GATT_SUCCESS) receiveValue(value, characteristic)
    }
    fun bluetoothGattCallbackCharacteristicChanged(value: ByteArray, characteristic: BluetoothGattCharacteristic, ) {
        receiveValue( value, characteristic)
    }

    private fun receiveValue(value: ByteArray, characteristic: BluetoothGattCharacteristic){
        when (characteristic.uuid) {
            uuidHeartRateMeasurement -> { heartRate.value = heartRateSDK(value, characteristic) }
            else -> { heartRateSDK(value, characteristic) }
        }
    }
    private fun heartRateSDK(value: ByteArray, characteristic: BluetoothGattCharacteristic): Int{
        val heartRate = if ( Build.VERSION.SDK_INT >= 33 ) value[1].dec().toInt()
                        else characteristic.value[1].dec().toInt()
//        Log.d("KDS", "heartRateSDK $heartRate")
        return heartRate
    }

    fun clearServicesCache(): Boolean {
        var result = false
        connection.value.gatt?.let { gattL ->
            try {
                val refreshMethod = gattL.javaClass.getMethod("refresh")
                result = refreshMethod.invoke(gattL) as Boolean
            } catch (e: Exception) {
//                messengerA.errorApi(R.string.error_refresh,": $e")
            }
        }
        return result
    }

    @SuppressLint("MissingPermission")
    fun disconnectDevice(gatt: BluetoothGatt? = connection.value.gatt) {
        gatt?.let {
            it.disconnect()
            it.close()
        }
    }

    @SuppressLint("MissingPermission")
    fun setCharacteristicNotification(gatt: BluetoothGatt, uuid: UUID, enabled: Boolean) {
        gatt.findCharacteristic(uuid)?.let{ characteristic ->
            gatt.setCharacteristicNotification(characteristic, enabled)
            val descriptor = characteristic.getDescriptor(uuidClientCharacteristicConfig)
            val descriptorValue = if (characteristic.isNotify()) { ENABLE_NOTIFICATION_VALUE
                                } else { ENABLE_INDICATION_VALUE }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                gatt.writeDescriptor(descriptor, descriptorValue)
            } else {
                descriptor.value = descriptorValue
                gatt.writeDescriptor(descriptor)
            }
        }
    }

    private fun BluetoothGatt.findCharacteristic(uuid: UUID): BluetoothGattCharacteristic?{
        if (services.isEmpty()) return null
        services.forEach { service ->
            service.characteristics.find { ch-> ch.uuid == uuid }.apply { if (this != null) return this }
        }
        return null
    }

    private fun BluetoothGattCharacteristic.containsProperty(property: Int) = properties and property != 0
    private fun BluetoothGattCharacteristic.isNotify(): Boolean =
        containsProperty(BluetoothGattCharacteristic.PROPERTY_NOTIFY)
//    fun BluetoothGattCharacteristic.isReadable(): Boolean =
//        containsProperty(BluetoothGattCharacteristic.PROPERTY_READ)
//    fun BluetoothGattCharacteristic.isWritable(): Boolean =
//        containsProperty(BluetoothGattCharacteristic.PROPERTY_WRITE)
//    fun BluetoothGattCharacteristic.isIndicatable(): Boolean =
//        containsProperty(BluetoothGattCharacteristic.PROPERTY_INDICATE)
}