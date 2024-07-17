package com.example.count_out.entity.bluetooth

import java.util.UUID

data class ParameterDeviceBle (
    var serviceUUID: UUID? = null,
    var characteristicUUID: UUID? = null,
    var descriptorUUID: UUID? = null,
    var value: ByteArray = byteArrayOf(0)
)