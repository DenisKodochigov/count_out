package com.count_out.domain.entity.bluetooth

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import com.count_out.domain.entity.enums.ErrorBleService
import com.count_out.domain.entity.router.DeviceBle
import kotlinx.coroutines.flow.MutableStateFlow

interface BleConnection: DeviceBle {
    override val name: String
    override val address: String
    val device: BluetoothDevice?
    val gatt: BluetoothGatt?

    val newState: Int
    val gattStatus: Int
}