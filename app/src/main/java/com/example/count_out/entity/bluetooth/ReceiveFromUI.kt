package com.example.count_out.entity.bluetooth

data class ReceiveFromUI(
    var addressForSearch: String = "",
    var currentConnection: BleConnection = BleConnection()
)
