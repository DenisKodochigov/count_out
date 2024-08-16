package com.example.count_out.entity.bluetooth

data class SendToUI(
    var heartRate: Int = 0,
    var scannedBle: Boolean = false,
    val connectingDevice: Boolean = false,
    var foundDevices: List<DeviceUI> = emptyList(),
    var lastConnectHearthRateDevice: DeviceUI? = null
){
    fun cancel(){
        heartRate = 0
    }
}
//
