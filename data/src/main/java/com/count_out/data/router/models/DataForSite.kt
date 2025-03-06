package com.count_out.data.router.models

import com.count_out.domain.entity.enums.RunningState
import kotlinx.coroutines.flow.MutableStateFlow

data class DataForSite (
    val site: String = "",
    val state: MutableStateFlow<RunningState> = MutableStateFlow(RunningState.Stopped),
)
