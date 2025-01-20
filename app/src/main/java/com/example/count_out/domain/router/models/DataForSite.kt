package com.example.count_out.domain.router.models

import com.example.count_out.entity.RunningState
import kotlinx.coroutines.flow.MutableStateFlow

data class DataForSite (
    val site: String = "",
    val state: MutableStateFlow<RunningState> = MutableStateFlow(RunningState.Stopped),
)
