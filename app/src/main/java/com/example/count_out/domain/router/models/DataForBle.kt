package com.example.count_out.domain.router.models

import com.example.count_out.device.bluetooth.modules.BleConnectionImpl

data class DataForBle (
    var addressForSearch: String = "",
    var currentConnection: BleConnectionImpl? = null,
)
