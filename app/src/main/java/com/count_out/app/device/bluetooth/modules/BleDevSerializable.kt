package com.count_out.app.device.bluetooth.modules

import com.count_out.app.entity.bluetooth.DeviceUI
import kotlinx.serialization.Serializable

@Serializable
data class BleDevSerializable(
    override var name: String = "",
    override var address: String = ""
): DeviceUI
