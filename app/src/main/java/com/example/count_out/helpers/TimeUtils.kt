package com.example.count_out.helpers

import com.example.count_out.entity.Const
import com.example.count_out.entity.StateRunning
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow

//suspend fun delayMy(delay: Long, pause: MutableState<Boolean>){
//    var count = 0
//    val delta = (delay/ Const.intervalDelay).toInt()
//    while (count <= delta){
//        if (!pause.value ) count++
//        delay(Const.intervalDelay)
//    }
//}
suspend fun delayMy(delay: Long, pause: MutableStateFlow<StateRunning>){
    var count = 0
    val delta = (delay/ Const.intervalDelay).toInt()
    while (count <= delta){
        if (pause.value != StateRunning.Pause) count++
        delay(Const.intervalDelay)
    }
}