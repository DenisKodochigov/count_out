package com.count_out.device.bluetooth.models

import kotlinx.serialization.Serializable

@Serializable
data class BleDevSerializable(
    override var name: String = "",
    override var address: String = ""
): com.count_out.domain.entity.router.DeviceUI
