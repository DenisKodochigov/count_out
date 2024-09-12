package com.example.count_out.entity

import com.example.count_out.entity.bluetooth.DeviceUI
import com.example.count_out.entity.no_use.MessageWorkOut
import kotlinx.coroutines.flow.MutableStateFlow

data class SendToUI (
    var flowTick: MutableStateFlow<TickTime> = MutableStateFlow(TickTime()),
    val message: MutableStateFlow<MessageWorkOut?> = MutableStateFlow(null),
    val set: MutableStateFlow<Set?> = MutableStateFlow(null),
    val nextSet: MutableStateFlow<Set?> = MutableStateFlow(null),
    val durationSpeech:MutableStateFlow<Pair<Long, Long>> = MutableStateFlow(Pair(0,0)),
    val runningState: MutableStateFlow<RunningState> = MutableStateFlow(RunningState.Stopped),
    var mark: MutableStateFlow<Mark> = MutableStateFlow(Mark()),

    var heartRate: Int = 0,
    var scannedBle: Boolean = false,
    val connectingState: ConnectState = ConnectState.NOT_CONNECTED,
    var foundDevices: List<DeviceUI> = emptyList(),
    var lastConnectHearthRateDevice: DeviceUI? = null,
){

    fun cancel(){
        flowTick.value = TickTime()
        set.value = null
        nextSet.value = null
        durationSpeech.value = Pair(0,0)
        runningState.value = RunningState.Stopped
        heartRate = 0
    }
}
