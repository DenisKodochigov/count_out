package com.example.count_out.entity.bluetooth

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import com.example.count_out.entity.enums.ErrorBleService
import kotlinx.coroutines.flow.MutableStateFlow

interface BleConnection: BleDevice {
    override var name: String
    override var address: String
    override var device: BluetoothDevice?
    var gatt: BluetoothGatt?
    val newState: MutableStateFlow<Int>
    val gattStatus: MutableStateFlow<Int>
    var error: MutableStateFlow<ErrorBleService>

}