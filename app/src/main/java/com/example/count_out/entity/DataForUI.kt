package com.example.count_out.entity

import com.example.count_out.entity.bluetooth.DeviceUI
import com.example.count_out.entity.router.Buffer
import com.example.count_out.entity.workout.Set
import kotlinx.coroutines.flow.MutableStateFlow

data class DataForUI (
    val speakingSet: MutableStateFlow<Set?> = MutableStateFlow(null),
    val runningState: MutableStateFlow<RunningState> = MutableStateFlow(RunningState.Stopped),

    val flowTick: MutableStateFlow<TickTime> = MutableStateFlow(TickTime()),
    val message: MutableStateFlow<MessageWorkOut?> = MutableStateFlow(null),
    val nextSet: MutableStateFlow<Set?> = MutableStateFlow(null),
    val durationSpeech:MutableStateFlow<Pair<Long, Long>> = MutableStateFlow(Pair(0,0)),

    val heartRate: MutableStateFlow<Int> = MutableStateFlow(0),
    val scannedBle: MutableStateFlow<Boolean> = MutableStateFlow(false),
    val connectingState: MutableStateFlow<ConnectState> = MutableStateFlow(ConnectState.NOT_CONNECTED),
    val foundDevices: MutableStateFlow<List<DeviceUI>> = MutableStateFlow(emptyList()),

    var cancelCoroutineWork: ()-> Unit = {}
){
    fun empty(){
        flowTick.value = TickTime()
        speakingSet.value = null
        nextSet.value = null
        durationSpeech.value = Pair(0,0)
        runningState.value = RunningState.Stopped
        heartRate.value = 0
    }
    fun set(buffer: Buffer){
        this.flowTick.value = buffer.flowTick.value
        this.message.value = buffer.message.value
        this.nextSet.value = buffer.nextSet.value
        this.durationSpeech.value = buffer.durationSpeech.value

        this.heartRate.value = buffer.heartRate.value
        this.scannedBle.value = buffer.scannedBle.value
        this.connectingState.value = buffer.connectingState.value
        this.foundDevices.value = buffer.foundDevices.value

    }
}
