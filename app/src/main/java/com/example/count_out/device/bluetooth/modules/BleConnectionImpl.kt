package com.example.count_out.device.bluetooth.modules

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import com.example.count_out.entity.ErrorBleService
import com.example.count_out.entity.bluetooth.BleDevice
import kotlinx.coroutines.flow.MutableStateFlow

class BleConnectionImpl (
    override var name: String = "",
    override var address: String = "",
    override var device: BluetoothDevice? = null,
    var gatt: BluetoothGatt? = null,

    val newState: MutableStateFlow<Int> = MutableStateFlow(BluetoothGatt.STATE_DISCONNECTED),
    val gattStatus: MutableStateFlow<Int> = MutableStateFlow(0),
    var error: MutableStateFlow<ErrorBleService> = MutableStateFlow(ErrorBleService.NONE),
): BleDevice