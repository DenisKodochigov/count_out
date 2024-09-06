package com.example.count_out.entity

import com.example.count_out.entity.bluetooth.DeviceUI
import com.example.count_out.entity.no_use.MessageWorkOut
import com.example.count_out.service.stopwatch.Watcher
import kotlinx.coroutines.flow.MutableStateFlow

data class SendToUI (
    var flowTick: MutableStateFlow<TickTime> = MutableStateFlow(TickTime()),
    val messageList: MutableStateFlow<List<MessageWorkOut>> = MutableStateFlow(emptyList()),
    val set: MutableStateFlow<Set?> = MutableStateFlow(null),
    val nextSet: MutableStateFlow<Set?> = MutableStateFlow(null),
    val durationSpeech:MutableStateFlow<Pair<Long, Long>> = MutableStateFlow(Pair(0,0)),
    val runningState: MutableStateFlow<RunningState> = MutableStateFlow(RunningState.Stopped),
    var heartRate: Int = 0,
    var scannedBle: Boolean = false,
    val connectingState: ConnectState = ConnectState.NOT_CONNECTED,
    var foundDevices: List<DeviceUI> = emptyList(),
    var lastConnectHearthRateDevice: DeviceUI? = null
){
    fun addMessage(text: String){
        val list: MutableList<MessageWorkOut> = messageList.value.toMutableList()
        list.add( MessageWorkOut(message = text, tickTime = Watcher.getTickTime().value))
        messageList.value = list
    }
    fun cancel(){
        flowTick.value = TickTime()
        messageList.value = emptyList()
        set.value = null
        nextSet.value = null
        durationSpeech.value = Pair(0,0)
        runningState.value = RunningState.Stopped
        heartRate = 0
    }
}
