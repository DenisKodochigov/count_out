package com.example.count_out.entity.router

import com.example.count_out.entity.ConnectState
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.TickTime
import com.example.count_out.entity.bluetooth.DeviceUI
import com.example.count_out.entity.workout.Coordinate
import com.example.count_out.entity.workout.MessageWorkOut
import com.example.count_out.entity.workout.Set
import kotlinx.coroutines.flow.MutableStateFlow

data class Buffer (
    val heartRate: MutableStateFlow<Int> = MutableStateFlow(0),
    val scannedBle: MutableStateFlow<Boolean> = MutableStateFlow(false),
    val connectingState: MutableStateFlow<ConnectState> = MutableStateFlow(ConnectState.NOT_CONNECTED),
    val foundDevices: MutableStateFlow<List<DeviceUI>> = MutableStateFlow(emptyList()),
    val lastConnectHearthRateDevice: MutableStateFlow<DeviceUI?> = MutableStateFlow(null),

    val flowTick: MutableStateFlow<TickTime> = MutableStateFlow(TickTime()),
    val message: MutableStateFlow<MessageWorkOut?> = MutableStateFlow(null),
    val speakingSet: MutableStateFlow<Set?> = MutableStateFlow(null),
    val nextSet: MutableStateFlow<Set?> = MutableStateFlow(null),
    val activityId: MutableStateFlow<Long?> = MutableStateFlow(null),
    val rest: MutableStateFlow<Int?> = MutableStateFlow(null),
    val durationSpeech:MutableStateFlow<Pair<Long, Long>> = MutableStateFlow(Pair(0,0)),
    val runningState: MutableStateFlow<RunningState> = MutableStateFlow(RunningState.Stopped),

    val coordinate: MutableStateFlow<Coordinate?> = MutableStateFlow( null),
)
