package com.count_out.domain.entity.router.models

import com.count_out.app.entity.RunningState
import kotlinx.coroutines.flow.MutableStateFlow

data class DataForSite (
    val site: String = "",
    val state: MutableStateFlow<RunningState> = MutableStateFlow(RunningState.Stopped),
)
