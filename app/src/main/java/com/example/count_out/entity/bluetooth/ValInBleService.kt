package com.example.count_out.entity.bluetooth

import kotlinx.coroutines.flow.MutableStateFlow

data class ValInBleService(
    val device: MutableStateFlow<BleDev> = MutableStateFlow(BleDev()),
)
