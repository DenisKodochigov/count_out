package com.example.count_out.entity.ui

import com.example.count_out.entity.ConnectState
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.TickTime
import com.example.count_out.entity.bluetooth.DeviceUI
import com.example.count_out.entity.router.Buffer
import com.example.count_out.entity.workout.Coordinate
import kotlinx.coroutines.flow.MutableStateFlow

data class DataForUI (
    val runningState: MutableStateFlow<RunningState?> = MutableStateFlow(null),

    val flowTime: MutableStateFlow<TickTime> = MutableStateFlow(TickTime()),
    val countRest: MutableStateFlow<Int> = MutableStateFlow(0),
    val currentCount: MutableStateFlow<Int> = MutableStateFlow(0),
    val currentDuration: MutableStateFlow<Int> = MutableStateFlow(0),
    val currentDistance: MutableStateFlow<Int> = MutableStateFlow(0),
    val enableChangeInterval: MutableStateFlow<Boolean> = MutableStateFlow(false),
    val executeInfoExercise: MutableStateFlow<ExecuteInfoExercise?> = MutableStateFlow(null),
    val executeInfoSet: MutableStateFlow<ExecuteInfoSet?> = MutableStateFlow(null),
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
//        this.currentCount.value = buffer.currentCount.value
//        this.currentDuration.value = buffer.currentDuration.value
//        this.currentDistance.value = buffer.currentDistance.value
//        this.executeInfoSet.value = buffer.executeInfoSet.value
//        this.executeInfoExercise.value = buffer.executeInfoExercise.value
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
