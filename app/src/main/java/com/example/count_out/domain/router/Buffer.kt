package com.example.count_out.domain.router

import com.example.count_out.entity.bluetooth.DeviceUI
import com.example.count_out.entity.enums.ConnectState
import com.example.count_out.entity.enums.RunningState
import com.example.count_out.services.timer.models.TickTimeImpl
import com.example.count_out.entity.workout.Coordinate
import com.example.count_out.entity.workout.StepTraining
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
