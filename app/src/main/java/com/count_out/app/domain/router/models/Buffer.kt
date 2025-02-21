package com.count_out.app.domain.router.models

import com.count_out.app.entity.ConnectState
import com.count_out.app.entity.RunningState
import com.count_out.app.entity.bluetooth.DeviceUI
import com.count_out.app.entity.workout.Coordinate
import com.count_out.app.entity.workout.StepTraining
import com.count_out.app.device.timer.models.TickTimeImpl
import kotlinx.coroutines.flow.MutableStateFlow

data class Buffer (
    val heartRate: MutableStateFlow<Int> = MutableStateFlow(0),
    val scannedBle: MutableStateFlow<Boolean> = MutableStateFlow(false),
    val bleConnectState: MutableStateFlow<ConnectState> = MutableStateFlow(ConnectState.NOT_CONNECTED),
    val foundDevices: MutableStateFlow<List<DeviceUI>> = MutableStateFlow(emptyList()),
    val lastConnectHearthRateDevice: MutableStateFlow<DeviceUI?> = MutableStateFlow(null),

    val flowTime: MutableStateFlow<TickTimeImpl> = MutableStateFlow(TickTimeImpl()),
    val countRest: MutableStateFlow<Int> = MutableStateFlow(0),
    val currentCount: MutableStateFlow<Int> = MutableStateFlow(0),
    val currentDuration: MutableStateFlow<Int> = MutableStateFlow(0),
    val currentDistance: MutableStateFlow<Int> = MutableStateFlow(0),
    val phaseWorkout: MutableStateFlow<Int> = MutableStateFlow(0),
    val enableChangeInterval: MutableStateFlow<Boolean> = MutableStateFlow(false),
    val stepTraining: MutableStateFlow<StepTraining?> = MutableStateFlow(null),
    val runningState: MutableStateFlow<RunningState?> = MutableStateFlow(null),
    val durationSpeech: MutableStateFlow<Pair<Long, Long>> = MutableStateFlow(Pair(0,0)),

    val coordinate: MutableStateFlow<Coordinate?> = MutableStateFlow( null),
)
