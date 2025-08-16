package com.count_out.device.bluetooth.models

import com.count_out.domain.entity.router.DeviceUI

sealed class TypeBle {
    data class Devices(val list: List<DeviceUI>): TypeBle()
    data class IntT(val item: Int): TypeBle()
}