package com.example.count_out.entity.bluetooth

import android.bluetooth.BluetoothDevice

data class BleDev (
    var device: BluetoothDevice? = null,
    var name: String = "",
    var address: String = "",
)