package com.example.count_out.entity

import kotlinx.coroutines.flow.MutableStateFlow

data class StreamsWorkout (
    val flowTick: MutableStateFlow<TickTime> = MutableStateFlow(TickTime(hour = "00", min="00", sec= "00")),
    val flowMessage: MutableStateFlow<StateWorkOut> = MutableStateFlow(StateWorkOut()),
)