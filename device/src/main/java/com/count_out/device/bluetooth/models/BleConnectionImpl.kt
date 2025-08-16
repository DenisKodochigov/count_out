package com.count_out.device.bluetooth.models

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import com.count_out.domain.entity.bluetooth.BleConnection
import com.count_out.domain.entity.router.DeviceBle

data class BleConnectionImpl (
    override val name: String = "",
    override val address: String = "",
    override val device: BluetoothDevice? = null,
    override val gatt: BluetoothGatt? = null,

    override val newState: Int = BluetoothGatt.STATE_DISCONNECTED,
    override val gattStatus: Int = 0,
): BleConnection {
    constructor(item: BleConnection): this(
        name = item.name,
        address = item.address,
        device = item.device,
        gatt = item.gatt,
        newState = item.newState,
//        error = item.error
    )
    @SuppressLint("MissingPermission")
    fun fromBluetoothDevice(device: BluetoothDevice): DeviceBle {
        return object : DeviceBle{
            override val name: String = device.name
            override val address: String = device.address
        }
    }
}