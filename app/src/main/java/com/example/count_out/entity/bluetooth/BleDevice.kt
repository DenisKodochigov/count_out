package com.example.count_out.entity.bluetooth

import android.bluetooth.BluetoothDevice

interface BleDevice: DeviceUI {
    override var name: String
    override var address: String
    var device: BluetoothDevice?
}