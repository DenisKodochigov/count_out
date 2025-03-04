package com.count_out.data.router.models

import com.count_out.entity.entity.bluetooth.BleConnection

data class DataForBle (
    var addressForSearch: String = "",
    var currentConnection: BleConnection? = null,
)
