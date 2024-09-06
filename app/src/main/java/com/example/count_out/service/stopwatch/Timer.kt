package com.example.count_out.service.stopwatch

import android.os.CountDownTimer
import android.os.SystemClock
import com.example.count_out.entity.RunningState
import com.example.count_out.ui.view_components.lg
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow

//class Timer {
//    val state: MutableStateFlow <TimerState> = MutableStateFlow(TimerState.END)
//    private var interval: Long = 100
//    private var startPaused: Long = 0
//    private var endCounting: MutableStateFlow <Long> = MutableStateFlow(0L)
//    private var startCounting: MutableStateFlow <Long> = MutableStateFlow(0L)
//
//    private fun run() {
//         CoroutineScope(Dispatchers.Default).launch {
//             while ( endCounting.value > System.currentTimeMillis() || state.value == TimerState.STOPPED){
//                 delay( interval) }
//             state.value = TimerState.END
//         }
//    }
//    fun changeState(command: TimerState, countMillSec: Long){
//        if (state.value == TimerState.COUNTING){
//            if (command == TimerState.STOPPED){
//                startPaused = System.currentTimeMillis()
//                state.value = TimerState.STOPPED
//            } else if (command == TimerState.END){ state.value = TimerState.END }
//        } else if (state.value == TimerState.STOPPED){
//            if (command == TimerState.COUNTING){
//                endCounting.value += System.currentTimeMillis() - startPaused
//                state.value = TimerState.COUNTING
//            } else if (command == TimerState.END){ state.value = TimerState.END }
//        } else if (state.value == TimerState.END){
//            if (command == TimerState.COUNTING){
//                startCounting.value = System.currentTimeMillis()
//                endCounting.value =  System.currentTimeMillis() + countMillSec
//                interval = calculateInterval(countMillSec)
//                state.value = TimerState.COUNTING
//                run()
//            }
//        }
//    }
//
//    suspend fun endCounting(callBack: ()-> Unit){
//        state.collect{
//            if ( it == TimerState.END) {
//                lg("End counting timer start: ${startCounting.value} - end: ${endCounting.value} = " +
//                        "${endCounting.value - startCounting.value}")
//                callBack()
//            }
//        }
//    }
//
//    private fun calculateInterval(countMillSec: Long): Long{
//        return if (countMillSec % 5 != 0L) 1L
//        else if (countMillSec % 10 != 0L) 5L
//        else if (countMillSec % 50 != 0L) 10L
//        else if (countMillSec % 100 != 0L) 50L
//        else if (countMillSec % 500 != 0L) 100L
//        else if (countMillSec % 1000 != 0L) 500L
//        else 1000L
//    }
//
//    fun state() = state
//}

suspend fun delayMy(delay: Long, pause: MutableStateFlow<RunningState>){
//    var count = 0
//    val delta = (delay / Const.INTERVAL_DELAY).toInt()
//    while (count <= delta){
//        if (pause.value == RunningState.Stopped) return
//        if (pause.value != RunningState.Paused) count++
//        delay(Const.INTERVAL_DELAY)
//    }
    DelMy().start(delay, pause)
}

class TimerMy {
    private lateinit var timer: CountDownTimer

    fun start(sec: Int, endCommand: ()-> Unit) {
        if (!this::timer.isInitialized) {
            timer = object : CountDownTimer(sec * 1000L, 1000) {
                override fun onTick(millisUntilFinished: Long) {}
                override fun onFinish() { endCommand() }
            }
            timer.start()
        }
    }
    fun cancel() { timer.cancel() }
}
class DelMy {
    var state: MutableStateFlow <RunningState> = MutableStateFlow(RunningState.Stopped)
    private var startTime: Long = 0
    private var pauseTime: Long = 0
    private var endTime: MutableStateFlow<Long> = MutableStateFlow(0)

    suspend fun start(milliSec: Long, stateF: MutableStateFlow<RunningState> ) {
        state = stateF
        startTime = SystemClock.elapsedRealtime()
        endTime.value = startTime + milliSec
        engine()
    }
    private suspend fun engine(){
        while (endTime.value > SystemClock.elapsedRealtime() ) {
            when (state.value){
                RunningState.Started -> {
                    if (pauseTime != 0L) {
                        endTime.value = pauseTime + (SystemClock.elapsedRealtime() - pauseTime)
                        pauseTime = 0L
                    }
                    lg("DelMy startTime=${startTime}, pauseTime=$pauseTime, endTime=${endTime.value}")
                }
                RunningState.Stopped -> {
                    endTime.value = SystemClock.elapsedRealtime()
                    lg("DelMy startTime=${startTime}, pauseTime=$pauseTime, endTime=${endTime.value}")
                }
                RunningState.Paused -> {
                    if (pauseTime == 0L) {
                        pauseTime = endTime.value
                        endTime.value += 100000000000L
                    }
                    lg("DelMy startTime=${startTime}, pauseTime=$pauseTime, endTime=${endTime.value}")
                }
            }
            delay(1)
        }
    }
    fun getTimerState() = state
}
