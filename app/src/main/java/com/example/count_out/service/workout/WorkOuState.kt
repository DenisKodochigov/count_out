package com.example.count_out.service.workout

import com.example.count_out.entity.StateRunning
import kotlinx.coroutines.flow.MutableStateFlow

data class WorkOuState(
    var stateRunning: MutableStateFlow<StateRunning> = MutableStateFlow(StateRunning.Stopped)
)
