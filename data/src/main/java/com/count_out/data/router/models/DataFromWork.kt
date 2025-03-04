package com.count_out.data.router.models

import com.count_out.entity.entity.StepTraining
import com.count_out.entity.entity.TickTime
import com.count_out.entity.enums.RunningState
import kotlinx.coroutines.flow.MutableStateFlow

data class DataFromWork (
    val runningState: MutableStateFlow<RunningState?> = MutableStateFlow(null),

    val flowTime: MutableStateFlow<TickTime?> = MutableStateFlow(null),
    val countRest: MutableStateFlow<Int> = MutableStateFlow(0),
    val currentCount: MutableStateFlow<Int> = MutableStateFlow(0),
    val currentDuration: MutableStateFlow<Int> = MutableStateFlow(0),
    val currentDistance: MutableStateFlow<Int> = MutableStateFlow(0),
    val enableChangeInterval: MutableStateFlow<Boolean> = MutableStateFlow(false),
    val phaseWorkout: MutableStateFlow<Int> = MutableStateFlow(0),
    val stepTraining: MutableStateFlow<StepTraining?> = MutableStateFlow(null),
    val durationSpeech: MutableStateFlow<Pair<Long, Long>> = MutableStateFlow(Pair(0, 0)),
    var trap: ()-> Unit = {},
    var trapNew: ()-> Unit = {}
){
    fun empty(){
        flowTime.value = null
        countRest.value = 0
        currentCount.value = 0
        currentDuration.value = 0
        currentDistance.value = 0
        enableChangeInterval.value = false
        phaseWorkout.value = 0
        stepTraining.value = null
        durationSpeech.value = Pair(0,0)
        this.trap = {}
        this.trapNew = {}
    }
}
