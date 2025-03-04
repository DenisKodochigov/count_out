package com.count_out.entity.entity.router

import android.util.Pair
import com.count_out.entity.entity.StepTraining
import com.count_out.entity.enums.RunningState
import com.count_out.entity.entity.Coordinate
import com.count_out.entity.entity.TickTime
import com.count_out.entity.enums.ConnectState
import kotlinx.coroutines.flow.MutableStateFlow

interface DataForUI {
    val runningState: MutableStateFlow<RunningState?>
    val flowTime: MutableStateFlow<TickTime?>
    val countRest: MutableStateFlow<Int>
    val currentCount: MutableStateFlow<Int>
    val currentDuration: MutableStateFlow<Int>
    val currentDistance: MutableStateFlow<Int>
    val enableChangeInterval: MutableStateFlow<Boolean>
    val stepTraining: MutableStateFlow<StepTraining?>
    val durationSpeech: MutableStateFlow<Pair<Long, Long>>

    val heartRate: MutableStateFlow<Int>
    val scannedBle: MutableStateFlow<Boolean>
    val bleConnectState: MutableStateFlow<ConnectState>
    val foundDevices: MutableStateFlow<List<DeviceUI>>
    val coordinate: MutableStateFlow<Coordinate?>
    var cancelCoroutineWork: ()-> Unit
    fun setWork(buffer: Buffer)
    fun setBle(buffer: Buffer)
}