package com.count_out.device.bluetooth.models

import com.count_out.domain.entity.router.DeviceUI

sealed class ResultBle {
    data class Success(val item: TypeBle): ResultBle()
    data class Devices(val list: List<DeviceUI>): ResultBle()
    data class Device(val device: DeviceUI): ResultBle()
    data object Nothing: ResultBle()
    data class Error(val throwable: ThrowableBle): ResultBle()
}