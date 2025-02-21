package com.count_out.app.device.bluetooth.modules

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import com.count_out.app.entity.ErrorBleService
import com.count_out.app.entity.bluetooth.BleDevice
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