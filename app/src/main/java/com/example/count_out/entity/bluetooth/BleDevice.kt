package com.example.count_out.entity.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice

open class BleDevice(
    override var name: String = "",
    override var address: String = "",
    open var device: BluetoothDevice? = null
): DeviceUI{
    @SuppressLint("MissingPermission")
    fun fromBluetoothDevice(device: BluetoothDevice): DeviceUI{
        this.device = device
        this.name = device.name ?: ""
        this.address = device.address
        return this
    }
}
