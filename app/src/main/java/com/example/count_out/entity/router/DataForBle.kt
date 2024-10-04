package com.example.count_out.entity.router

import com.example.count_out.entity.bluetooth.BleConnection

data class DataForBle (
    var addressForSearch: String = "",
    var currentConnection: BleConnection? = null,
)
