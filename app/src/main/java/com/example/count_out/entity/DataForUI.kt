package com.example.count_out.entity

import com.example.count_out.entity.bluetooth.DeviceUI
import kotlinx.coroutines.flow.MutableStateFlow

data class DataForUI (
    var flowTick: MutableStateFlow<TickTime> = MutableStateFlow(TickTime()),
    val message: MutableStateFlow<MessageWorkOut?> = MutableStateFlow(null),
    val set: MutableStateFlow<Set?> = MutableStateFlow(null),
    val nextSet: MutableStateFlow<Set?> = MutableStateFlow(null),
    val durationSpeech:MutableStateFlow<Pair<Long, Long>> = MutableStateFlow(Pair(0,0)),
    val runningState: MutableStateFlow<RunningState> = MutableStateFlow(RunningState.Stopped),
    var mark: MutableStateFlow<Mark> = MutableStateFlow(Mark()),

    val heartRate: MutableStateFlow<Int> = MutableStateFlow(0),
    var scannedBle: MutableStateFlow<Boolean> = MutableStateFlow(false),
    val connectingState: MutableStateFlow<ConnectState> = MutableStateFlow(ConnectState.NOT_CONNECTED),
    var foundDevices: MutableStateFlow<List<DeviceUI>> = MutableStateFlow(emptyList()),
    var lastConnectHearthRateDevice: MutableStateFlow<DeviceUI?> = MutableStateFlow(null),

    var cancelCoroutineWork: ()-> Unit = {}
){

    fun empty(){
        flowTick.value = TickTime()
        set.value = null
        nextSet.value = null
        durationSpeech.value = Pair(0,0)
        runningState.value = RunningState.Stopped
        heartRate.value = 0
    }
}
