package com.example.count_out.entity.router

import com.example.count_out.entity.RunningState
import com.example.count_out.entity.TickTime
import com.example.count_out.entity.ui.ExecuteSetInfo
import kotlinx.coroutines.flow.MutableStateFlow

data class DataFromWork (
    val runningState: MutableStateFlow<RunningState?> = MutableStateFlow(null),

    val flowTime: MutableStateFlow<TickTime> = MutableStateFlow(TickTime()),
    val countRest: MutableStateFlow<Int> = MutableStateFlow(0),
    val currentCount: MutableStateFlow<Int> = MutableStateFlow(0),
    val currentDuration: MutableStateFlow<Int> = MutableStateFlow(0),
    val currentDistance: MutableStateFlow<Int> = MutableStateFlow(0),
    val enableChangeInterval: MutableStateFlow<Boolean> = MutableStateFlow(false),
    val phaseWorkout: MutableStateFlow<Int> = MutableStateFlow(0),
    val exerciseInfo: MutableStateFlow<ExecuteSetInfo?> = MutableStateFlow(null),
    val durationSpeech: MutableStateFlow<Pair<Long, Long>> = MutableStateFlow(Pair(0,0)),
    var equalsStop: ()-> Unit = {}
){
    fun empty(){
        this.flowTime.value = TickTime()
        this.countRest.value = 0
        this.currentCount.value = 0
        this.currentDuration.value = 0
        this.currentDistance.value = 0
        this.phaseWorkout.value = 0
        this.enableChangeInterval.value = false
        this.exerciseInfo.value = null
        this.runningState.value = RunningState.Stopped
        this.durationSpeech.value = Pair(0,0)
        this.equalsStop = {}
    }
}
