package com.count_out.domain.entity.bluetooth

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import kotlinx.coroutines.flow.MutableStateFlow

interface BleConnection: BleDevice {
    override val name: String
    override val address: String
    val device: BluetoothDevice?
    val gatt: BluetoothGatt?

    val newState: MutableStateFlow<Int>
    val gattStatus: MutableStateFlow<Int>
    val error: MutableStateFlow<ErrorBleService>
}