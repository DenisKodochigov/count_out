package com.example.count_out.service.bluetooth

import com.example.count_out.entity.StateRunning
import kotlinx.coroutines.flow.MutableStateFlow

data class BleState(
    var stateRunning: MutableStateFlow<StateRunning> = MutableStateFlow(StateRunning.Stopped)
)
