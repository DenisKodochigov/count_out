package com.example.count_out.service.bluetooth

import com.example.count_out.entity.RunningState
import kotlinx.coroutines.flow.MutableStateFlow

data class BleState(
    var runningState: MutableStateFlow<RunningState> = MutableStateFlow(RunningState.Stopped)
)
