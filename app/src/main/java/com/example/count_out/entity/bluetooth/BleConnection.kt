package com.example.count_out.entity.bluetooth

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattService
import kotlinx.coroutines.flow.MutableStateFlow

data class BleConnection (
    var device: BluetoothDevice? = null,
    var services: List<BluetoothGattService>? = null,
    var gatt: BluetoothGatt? = null,
    var name: String = "",
    var address: String = "",
    val newStateLoc: MutableStateFlow<Int> = MutableStateFlow(BluetoothGatt.STATE_DISCONNECTED),
    val connectionStatus: MutableStateFlow<Int> = MutableStateFlow(0),
    val boundState: MutableStateFlow<Int> = MutableStateFlow(0),
)