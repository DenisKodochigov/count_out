package com.example.count_out.entity.router

import com.example.count_out.entity.RunningState
import com.example.count_out.entity.TickTime
import com.example.count_out.entity.workout.MessageWorkOut
import com.example.count_out.entity.workout.Set
import kotlinx.coroutines.flow.MutableStateFlow

data class DataFromWork (
    val flowTick: MutableStateFlow<TickTime> = MutableStateFlow(TickTime()),
    val message: MutableStateFlow<MessageWorkOut?> = MutableStateFlow(null),
    val speakingSet: MutableStateFlow<Set?> = MutableStateFlow(null),
    val rest: MutableStateFlow<Int?> = MutableStateFlow(null),
    val activityId: MutableStateFlow<Long?> = MutableStateFlow(null),
    val nextSet: MutableStateFlow<Set?> = MutableStateFlow(null),
    val durationSpeech: MutableStateFlow<Pair<Long, Long>> = MutableStateFlow(Pair(0,0)),
    val runningState: MutableStateFlow<RunningState> = MutableStateFlow(RunningState.Stopped),
    var equalsStop: ()-> Unit = {}
){
    fun empty(){
        this.flowTick.value = TickTime()
        this.message.value = null
        this.speakingSet.value = null
        this.rest.value = null
        this.nextSet.value = null
        this.durationSpeech.value = Pair(0,0)
        this.runningState.value = RunningState.Stopped
        this.equalsStop = {}
    }
}
