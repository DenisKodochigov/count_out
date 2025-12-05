package com.count_out.domain.entity.router

import com.count_out.domain.entity.bluetooth.BleConnection

data class DataForBle (
    var addressForSearch: String = "",
    var currentConnection: BleConnection? = null,
)
