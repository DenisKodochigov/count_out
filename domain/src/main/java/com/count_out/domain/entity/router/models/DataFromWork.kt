package com.count_out.domain.entity.router.models

import com.count_out.app.entity.RunningState
import com.count_out.app.entity.workout.StepTraining
import com.count_out.app.device.timer.models.TickTimeImpl
import kotlinx.coroutines.flow.MutableStateFlow

data class DataFromWork (
    val runningState: MutableStateFlow<RunningState?> = MutableStateFlow(null),

    val flowTime: MutableStateFlow<TickTimeImpl> = MutableStateFlow(TickTimeImpl()),
    val countRest: MutableStateFlow<Int> = MutableStateFlow(0),
    val currentCount: MutableStateFlow<Int> = MutableStateFlow(0),
    val currentDuration: MutableStateFlow<Int> = MutableStateFlow(0),
    val currentDistance: MutableStateFlow<Int> = MutableStateFlow(0),
    val enableChangeInterval: MutableStateFlow<Boolean> = MutableStateFlow(false),
    val phaseWorkout: MutableStateFlow<Int> = MutableStateFlow(0),
    val stepTraining: MutableStateFlow<StepTraining?> = MutableStateFlow(null),
    val durationSpeech: MutableStateFlow<Pair<Long, Long>> = MutableStateFlow(Pair(0,0)),
    var trap: ()-> Unit = {},
    var trapNew: ()-> Unit = {}
){
    fun empty(){
        this.flowTime.value = TickTimeImpl()
        this.countRest.value = 0
        this.currentCount.value = 0
        this.currentDuration.value = 0
        this.currentDistance.value = 0
        this.phaseWorkout.value = 0
        this.enableChangeInterval.value = false
        this.runningState.value = RunningState.Stopped
        this.durationSpeech.value = Pair(0,0)
        this.trap = {}
        this.trapNew = {}
    }
}
