package com.example.count_out.helpers

import com.example.count_out.entity.Const
import com.example.count_out.entity.RunningState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
suspend fun delayMy(delay: Long, pause: MutableStateFlow<RunningState>){
    var count = 0
    val delta = (delay/ Const.INTERVAL_DELAY).toInt()
    while (count <= delta){
        if (pause.value == RunningState.Stopped) return
        if (pause.value != RunningState.Paused) count++
        delay(Const.INTERVAL_DELAY)
    }
}