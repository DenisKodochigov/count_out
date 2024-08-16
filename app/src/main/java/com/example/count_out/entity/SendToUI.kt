package com.example.count_out.entity

import com.example.count_out.entity.bluetooth.DeviceUI
import com.example.count_out.entity.no_use.MessageWorkOut
import com.example.count_out.service.stopwatch.StopWatchObj
import kotlinx.coroutines.flow.MutableStateFlow

data class SendToUI (
    var flowTick: MutableStateFlow<TickTime> = MutableStateFlow(TickTime()),
    val messageList: MutableStateFlow<List<MessageWorkOut>> = MutableStateFlow(emptyList()),
    val set: MutableStateFlow<Set?> = MutableStateFlow(null),
    val durationSpeech:MutableStateFlow<Pair<Long, Long>> = MutableStateFlow(Pair(0,0)),
    val stateRunning: MutableStateFlow<StateRunning> = MutableStateFlow(StateRunning.Created),
    var heartRate: Int = 0,
    var scannedBle: Boolean = false,
    val connectingDevice: ConnectState = ConnectState.NOT_CONNECTED,
    var foundDevices: List<DeviceUI> = emptyList(),
    var lastConnectHearthRateDevice: DeviceUI? = null
){
    fun addMessage(text: String){
        val list: MutableList<MessageWorkOut> = messageList.value.toMutableList()
        list.add( MessageWorkOut(message = text, tickTime = StopWatchObj.getTickTime().value))
        messageList.value = list
    }
    fun cancel(){
        messageList.value = emptyList()
        stateRunning.value = StateRunning.Stopped
        durationSpeech.value = Pair(0,0)
        heartRate = 0
    }
}
