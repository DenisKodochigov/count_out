package com.example.count_out.domain.router

import com.example.count_out.data.bluetooth.modules.BleConnection

data class DataForBle (
    var addressForSearch: String = "",
    var currentConnection: BleConnection? = null,
)
