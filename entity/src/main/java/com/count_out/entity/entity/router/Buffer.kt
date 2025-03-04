package com.count_out.entity.entity.router

import com.count_out.entity.enums.ConnectState
import com.count_out.entity.entity.StepTraining
import com.count_out.entity.entity.Coordinate
import com.count_out.entity.enums.RunningState
import com.count_out.entity.entity.TickTime
import kotlinx.coroutines.flow.MutableStateFlow

//data class Buffer (
//    val heartRate: MutableStateFlow<Int> = MutableStateFlow(0),
//    val scannedBle: MutableStateFlow<Boolean> = MutableStateFlow(false),
//    val bleConnectState: MutableStateFlow<ConnectState> = MutableStateFlow(ConnectState.NOT_CONNECTED),
//    val foundDevices: MutableStateFlow<List<DeviceUI>> = MutableStateFlow(emptyList()),
//    val lastConnectHearthRateDevice: MutableStateFlow<DeviceUI?> = MutableStateFlow(null),
//
//    val flowTime: MutableStateFlow<TickTime?> = MutableStateFlow(null),
//    val countRest: MutableStateFlow<Int> = MutableStateFlow(0),
//    val currentCount: MutableStateFlow<Int> = MutableStateFlow(0),
//    val currentDuration: MutableStateFlow<Int> = MutableStateFlow(0),
//    val currentDistance: MutableStateFlow<Int> = MutableStateFlow(0),
//    val phaseWorkout: MutableStateFlow<Int> = MutableStateFlow(0),
//    val enableChangeInterval: MutableStateFlow<Boolean> = MutableStateFlow(false),
//    val stepTraining: MutableStateFlow<StepTraining?> = MutableStateFlow(null),
//    val runningState: MutableStateFlow<RunningState?> = MutableStateFlow(null),
//    val durationSpeech: MutableStateFlow<Pair<Long, Long>> = MutableStateFlow(Pair(0, 0)),
//
//    val coordinate: MutableStateFlow<Coordinate?> = MutableStateFlow(null),
//)
interface Buffer {
    val heartRate: MutableStateFlow<Int>
    val scannedBle: MutableStateFlow<Boolean>
    val bleConnectState: MutableStateFlow<ConnectState>
    val foundDevices: MutableStateFlow<List<DeviceUI>>
    val lastConnectHearthRateDevice: MutableStateFlow<DeviceUI?>

    val flowTime: MutableStateFlow<TickTime?>
    val countRest: MutableStateFlow<Int>
    val currentCount: MutableStateFlow<Int>
    val currentDuration: MutableStateFlow<Int>
    val currentDistance: MutableStateFlow<Int>
    val phaseWorkout: MutableStateFlow<Int>
    val enableChangeInterval: MutableStateFlow<Boolean>
    val stepTraining: MutableStateFlow<StepTraining?>
    val runningState: MutableStateFlow<RunningState?>
    val durationSpeech: MutableStateFlow<Pair<Long, Long>>

    val coordinate: MutableStateFlow<Coordinate?>
}
