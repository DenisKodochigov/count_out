package com.count_out.device.bluetooth.ai

import android.Manifest
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.content.Context
import androidx.annotation.RequiresPermission
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.util.UUID

class BleHeartRateProvider(
    private val context: Context,
    private val deviceAddress: String,
    override val heartRateFlow: Flow<Int>
): HeartRateProvider  {
    private val _hr = MutableSharedFlow<Int>(replay = 0, extraBufferCapacity = 16)
    fun heartRateFlow(): SharedFlow<Int> = _hr.asSharedFlow()
    private var bluetoothGatt: BluetoothGatt? = null
    private var scope: CoroutineScope? = null
    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    fun start(scope: CoroutineScope) {
        this.scope = scope
        val bluetoothManager =
            context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val bluetoothAdapter = bluetoothManager.adapter
        val device = bluetoothAdapter.getRemoteDevice(deviceAddress)
        bluetoothGatt = device.connectGatt(context, false, gattCallback)
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    fun stop() {
        bluetoothGatt?.close()
        bluetoothGatt = null
    }
    private val gattCallback = object : BluetoothGattCallback() {
        @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                gatt?.discoverServices()
            }
        }

        @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            val hrService =
                gatt?.getService(UUID.fromString("0000180d-0000-1000-8000-00805f9b34fb"))
            val hrChar =
                hrService?.getCharacteristic(UUID.fromString("00002a37-0000-1000-8000-00805f9b34fb"))
            if (hrChar != null) {
                gatt.setCharacteristicNotification(hrChar, true)
                val descriptor =
                    hrChar.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"))
                descriptor.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
                gatt.writeDescriptor(descriptor)
            }
        }
    }

    override suspend fun connect() {
        TODO("Not yet implemented")
    }

    override suspend fun disconnect() {
        TODO("Not yet implemented")
    }
}