package com.example.count_out.domain.router.models

import com.example.count_out.entity.ConnectState
import com.example.count_out.entity.bluetooth.DeviceUI
import kotlinx.coroutines.flow.MutableStateFlow

data class DataFromBle (
    val heartRate: MutableStateFlow<Int> = MutableStateFlow(0),
    var scannedBle: MutableStateFlow<Boolean> = MutableStateFlow(false),
    val connectingState: MutableStateFlow<ConnectState> = MutableStateFlow(ConnectState.NOT_CONNECTED),
    var foundDevices: MutableStateFlow<List<DeviceUI>> = MutableStateFlow(emptyList()),
    var lastConnectHearthRateDevice: MutableStateFlow<DeviceUI?> = MutableStateFlow(null),
){
//    fun empty(){
//        this.heartRate.value = 0
//        this.scannedBle.value = false
//        this.connectingState.value = ConnectState.NOT_CONNECTED
//        this.foundDevices.value = emptyList()
//        this.lastConnectHearthRateDevice.value = null
//    }
}
