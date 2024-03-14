package com.example.count_out.entity

import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Singleton

@Singleton
data class StreamsWorkout (
    var flowTick: MutableStateFlow<TickTime> = MutableStateFlow(TickTime(hour = "00", min="00", sec= "00")),
    var flowMessage: MutableStateFlow<StateWorkOut> = MutableStateFlow(StateWorkOut()),
)