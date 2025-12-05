package com.count_out.domain.entity.router

import com.count_out.domain.entity.StepPlan
import com.count_out.domain.entity.TickTime
import com.count_out.domain.entity.enums.RunningState
import com.count_out.domain.entity.workout.Domain

data class FromWork (
    val runningState: RunningState = RunningState.Binding,
    val flowTime: TickTime? =null,
    val countRest: Int =0,
    val currentCount: Int = 0,
    val currentDuration: Int = 0,
    val currentDistance: Int = 0,
    val enableChangeInterval: Boolean = false,
    val phaseWorkout: Int = 0,
    val stepTraining: StepPlan? = null,
    val durationSpeech: Pair<Long, Long> = Pair(0, 0),
    var trap: ()-> Unit = {},
    var trapNew: ()-> Unit = {}
): Domain {

//    fun empty(){
//        flowTime = null
//        countRest = 0
//        currentCount = 0
//        currentDuration = 0
//        currentDistance = 0
//        enableChangeInterval = false
//        phaseWorkout = 0
//        stepTraining = null
//        durationSpeech = Pair(0,0)
//        this.trap = {}
//        this.trapNew = {}
//    }
}
