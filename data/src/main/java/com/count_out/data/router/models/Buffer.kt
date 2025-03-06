package com.count_out.data.router.models

import com.count_out.domain.entity.Coordinate
import com.count_out.domain.entity.StepTraining
import com.count_out.domain.entity.TickTime
import com.count_out.domain.entity.enums.ConnectState
import com.count_out.domain.entity.enums.RunningState
import com.count_out.domain.entity.router.Buffer
import com.count_out.domain.entity.router.DeviceUI
import kotlinx.coroutines.flow.MutableStateFlow

data class Buffer (
    override val heartRate: MutableStateFlow<Int> = MutableStateFlow(0),
    override val scannedBle: MutableStateFlow<Boolean> = MutableStateFlow(false),
    override val bleConnectState: MutableStateFlow<ConnectState> = MutableStateFlow(
        ConnectState.NOT_CONNECTED
    ),
    override val foundDevices: MutableStateFlow<List<DeviceUI>> = MutableStateFlow(emptyList()),
    override val lastConnectHearthRateDevice: MutableStateFlow<DeviceUI?> = MutableStateFlow(null),

    override val flowTime: MutableStateFlow<TickTime?> = MutableStateFlow(null),
    override val countRest: MutableStateFlow<Int> = MutableStateFlow(0),
    override val currentCount: MutableStateFlow<Int> = MutableStateFlow(0),
    override val currentDuration: MutableStateFlow<Int> = MutableStateFlow(0),
    override val currentDistance: MutableStateFlow<Int> = MutableStateFlow(0),
    override val phaseWorkout: MutableStateFlow<Int> = MutableStateFlow(0),
    override val enableChangeInterval: MutableStateFlow<Boolean> = MutableStateFlow(false),
    override val stepTraining: MutableStateFlow<StepTraining?> = MutableStateFlow(null),
    override val runningState: MutableStateFlow<RunningState?> = MutableStateFlow(null),
    override val durationSpeech: MutableStateFlow<Pair<Long, Long>> = MutableStateFlow(Pair(0, 0)),

    override val coordinate: MutableStateFlow<Coordinate?> = MutableStateFlow(null),
): Buffer
