package com.count_out.device.bluetooth.models

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice

open class BleDeviceImpl(
    override var name: String = "",
    override var address: String = "",
    open var device: BluetoothDevice? = null
): com.count_out.domain.entity.router.DeviceUI {
    @SuppressLint("MissingPermission")
    fun fromBluetoothDevice(device: BluetoothDevice): com.count_out.domain.entity.router.DeviceUI {
        this.device = device
        this.name = device.name ?: ""
        this.address = device.address
        return this
    }
}
