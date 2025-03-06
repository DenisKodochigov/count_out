package com.count_out.device.bluetooth.models

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import com.count_out.domain.entity.enums.ErrorBleService
import kotlinx.coroutines.flow.MutableStateFlow

class BleConnectionImpl (
    override var name: String = "",
    override var address: String = "",
    override var device: BluetoothDevice? = null,
    override var gatt: BluetoothGatt? = null,

    override val newState: MutableStateFlow<Int> = MutableStateFlow(BluetoothGatt.STATE_DISCONNECTED),
    override val gattStatus: MutableStateFlow<Int> = MutableStateFlow(0),
    override var error: MutableStateFlow<com.count_out.domain.entity.enums.ErrorBleService> = MutableStateFlow(
        com.count_out.domain.entity.enums.ErrorBleService.NONE
    ),
): com.count_out.domain.entity.bluetooth.BleConnection