package com.example.count_out.service.workout

import com.example.count_out.entity.RunningState
import kotlinx.coroutines.flow.MutableStateFlow

data class WorkOuState(
    var runningState: MutableStateFlow<RunningState> = MutableStateFlow(RunningState.Stopped)
)
