package com.example.count_out.entity

import android.bluetooth.BluetoothDevice

data class BluetoothDeviceApp (
    var device: BluetoothDevice? = null,
    var name: String = "",
    var address: String = ""
)