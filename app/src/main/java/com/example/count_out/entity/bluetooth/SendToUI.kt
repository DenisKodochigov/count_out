package com.example.count_out.entity.bluetooth

import kotlinx.coroutines.flow.MutableStateFlow

data class SendToUI(
    val rate: MutableStateFlow<Int> = MutableStateFlow(0),
    val foundDevices:  MutableStateFlow<List<DeviceUI>> = MutableStateFlow(emptyList()),
){
    fun cancel(){
        rate.value = 0
    }
}
//
