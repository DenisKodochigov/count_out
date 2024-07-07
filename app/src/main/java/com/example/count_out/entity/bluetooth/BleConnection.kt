package com.example.count_out.entity.bluetooth

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.UUID

data class BleConnection (
    var device: BluetoothDevice? = null,
    var gatt: BluetoothGatt? = null,
    var name: String = "",
    var address: String = "",
    val newState: MutableStateFlow<Int> = MutableStateFlow(BluetoothGatt.STATE_DISCONNECTED),
    val connectStatus: MutableStateFlow<Int> = MutableStateFlow(0),
    val boundState: MutableStateFlow<Int> = MutableStateFlow(0),
    val characteristic: BluetoothGattCharacteristic? = null,
    val characteristics: List<BluetoothGattCharacteristic> = emptyList(),
    val descriptorUuid: UUID = UUID.randomUUID(),
    val descriptor: BluetoothGattDescriptor? = null,
)