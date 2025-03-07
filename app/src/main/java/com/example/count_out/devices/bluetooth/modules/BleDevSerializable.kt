package com.example.count_out.devices.bluetooth.modules

import com.example.count_out.entity.bluetooth.DeviceUI
import kotlinx.serialization.Serializable

@Serializable
data class BleDevSerializable(
    override var name: String = "",
    override var address: String = ""
): DeviceUI
