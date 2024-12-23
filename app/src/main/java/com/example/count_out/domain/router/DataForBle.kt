package com.example.count_out.domain.router

import com.example.count_out.data.bluetooth.modules.BleConnectionImpl

data class DataForBle (
    var addressForSearch: String = "",
    var currentConnection: BleConnectionImpl? = null,
)
