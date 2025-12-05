package com.count_out.domain.entity.router

import com.count_out.domain.entity.enums.ConnectState
import kotlinx.coroutines.flow.MutableStateFlow

data class DataFromBle (
    val heartRate: MutableStateFlow<Int> = MutableStateFlow(0),
    var scannedBle: MutableStateFlow<Boolean> = MutableStateFlow(false),
    val connectingState: MutableStateFlow<ConnectState> = MutableStateFlow(ConnectState.NOT_CONNECTED),
    var foundDevices: MutableStateFlow<List<DeviceBle>> = MutableStateFlow(emptyList()),
    var lastConnectHearthRateDevice: MutableStateFlow<DeviceBle?> = MutableStateFlow(null),
){
//    fun empty(){
//        this.heartRate.value = 0
//        this.scannedBle.value = false
//        this.connectingState.value = ConnectState.NOT_CONNECTED
//        this.foundDevices.value = emptyList()
//        this.lastConnectHearthRateDevice.value = null
//    }
}
