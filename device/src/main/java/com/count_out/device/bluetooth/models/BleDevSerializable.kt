package com.count_out.device.bluetooth.models

import com.count_out.entity.entity.router.DeviceUI
import kotlinx.serialization.Serializable

@Serializable
data class BleDevSerializable(
    override var name: String = "",
    override var address: String = ""
): DeviceUI
