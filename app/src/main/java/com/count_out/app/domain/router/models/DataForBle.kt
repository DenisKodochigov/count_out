package com.count_out.app.domain.router.models

import com.count_out.app.device.bluetooth.modules.BleConnectionImpl

data class DataForBle (
    var addressForSearch: String = "",
    var currentConnection: BleConnectionImpl? = null,
)
