package com.count_out.data.router.models

import com.count_out.domain.entity.Coordinate
import com.count_out.domain.entity.StepTraining
import com.count_out.domain.entity.TickTime
import com.count_out.domain.entity.router.Buffer
import com.count_out.domain.entity.router.DeviceUI
import com.count_out.domain.entity.enums.ConnectState
import com.count_out.domain.entity.enums.RunningState
import kotlinx.coroutines.flow.MutableStateFlow

data class DataForUI (
    val runningState: MutableStateFlow<RunningState?> = MutableStateFlow(null),

    val flowTime: MutableStateFlow<TickTime?> = MutableStateFlow(null),
    val countRest: MutableStateFlow<Int> = MutableStateFlow(0),
    val currentCount: MutableStateFlow<Int> = MutableStateFlow(0),
    val currentDuration: MutableStateFlow<Int> = MutableStateFlow(0),
    val currentDistance: MutableStateFlow<Int> = MutableStateFlow(0),
    val enableChangeInterval: MutableStateFlow<Boolean> = MutableStateFlow(false),
    val stepTraining: MutableStateFlow<StepTraining?> = MutableStateFlow(null),
    val durationSpeech: MutableStateFlow<Pair<Long, Long>> = MutableStateFlow(Pair(0,0)),

    val heartRate: MutableStateFlow<Int> = MutableStateFlow(0),
    val scannedBle: MutableStateFlow<Boolean> = MutableStateFlow(false),
    val bleConnectState: MutableStateFlow<ConnectState> = MutableStateFlow(ConnectState.NOT_CONNECTED),
    val foundDevices: MutableStateFlow<List<DeviceUI>> = MutableStateFlow(emptyList()),
    val coordinate: MutableStateFlow<Coordinate?> = MutableStateFlow( null),
    var cancelCoroutineWork: ()-> Unit = {}
){
    fun setWork(buffer: Buffer){
        this.flowTime.value = buffer.flowTime.value
        this.countRest.value = buffer.countRest.value
        this.enableChangeInterval.value = buffer.enableChangeInterval.value
        this.durationSpeech.value = buffer.durationSpeech.value
    }
    fun setBle(buffer: Buffer){
        this.heartRate.value = buffer.heartRate.value
        this.scannedBle.value = buffer.scannedBle.value
        this.bleConnectState.value = buffer.bleConnectState.value
        this.foundDevices.value = buffer.foundDevices.value
        this.coordinate.value = buffer.coordinate.value
    }
}
