package com.example.count_out.entity.bluetooth

data class SendToBle(
    var addressForSearch: String = "",
    var currentConnection: BleConnection? = null
)
