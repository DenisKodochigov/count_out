package com.example.count_out.entity.bluetooth

import kotlinx.serialization.Serializable

@Serializable
data class BleDevSerializable(
    val name: String = "",
    val mac: String = ""
)
