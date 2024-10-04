package com.example.count_out.entity.router

import com.example.count_out.entity.Mark
import com.example.count_out.entity.MessageWorkOut
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.TickTime
import com.example.count_out.entity.workout.Set
import kotlinx.coroutines.flow.MutableStateFlow

data class DataFromWork (
    var flowTick: MutableStateFlow<TickTime> = MutableStateFlow(TickTime()),
    val message: MutableStateFlow<MessageWorkOut?> = MutableStateFlow(null),
    val set: MutableStateFlow<Set?> = MutableStateFlow(null),
    val nextSet: MutableStateFlow<Set?> = MutableStateFlow(null),
    val durationSpeech: MutableStateFlow<Pair<Long, Long>> = MutableStateFlow(Pair(0,0)),
    val runningState: MutableStateFlow<RunningState> = MutableStateFlow(RunningState.Stopped),
    var mark: MutableStateFlow<Mark> = MutableStateFlow(Mark()),
    var cancelCoroutineWork: ()-> Unit = {}
)
