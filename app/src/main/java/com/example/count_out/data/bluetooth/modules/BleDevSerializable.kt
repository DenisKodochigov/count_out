package com.example.count_out.data.bluetooth.modules

import kotlinx.serialization.Serializable

@Serializable
data class BleDevSerializable(
    override var name: String = "",
    override var address: String = ""
): DeviceUI
