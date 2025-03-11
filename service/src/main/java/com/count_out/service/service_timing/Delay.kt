package com.count_out.service.service_timing

import android.os.SystemClock
import com.count_out.domain.entity.enums.RunningState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow

class Delay{

    suspend fun run(delay: Long, pause: MutableStateFlow<RunningState?>){
        val startTime: Long = SystemClock.elapsedRealtime()
        var pauseTime: Long = 0
        val endTime: MutableStateFlow<Long> = MutableStateFlow(startTime + delay)
        while (endTime.value > SystemClock.elapsedRealtime() ) {
            when (pause.value){
                RunningState.Started -> {
                    if (pauseTime != 0L) {
                        endTime.value = pauseTime + (SystemClock.elapsedRealtime() - pauseTime)
                        pauseTime = 0L
                    }
                }
                RunningState.Stopped -> {
                    endTime.value = SystemClock.elapsedRealtime()
                }
                RunningState.Paused -> {
                    if (pauseTime == 0L) {
                        pauseTime = endTime.value
                        endTime.value += 100000000000L
                    }
                }
                else-> {}
            }
            delay(1)
        }
    }
}

//suspend fun delayMy(delay: Long, pause: MutableStateFlow<RunningState?>){
//    val state: MutableStateFlow <RunningState?> = pause
//    val startTime: Long = SystemClock.elapsedRealtime()
//    var pauseTime: Long = 0
//    val endTime: MutableStateFlow<Long> = MutableStateFlow(startTime + delay)
//    while (endTime.value > SystemClock.elapsedRealtime() ) {
//        when (state.value){
//            RunningState.Started -> {
//                if (pauseTime != 0L) {
//                    endTime.value = pauseTime + (SystemClock.elapsedRealtime() - pauseTime)
//                    pauseTime = 0L
//                }
//            }
//            RunningState.Stopped -> {
//                endTime.value = SystemClock.elapsedRealtime()
//            }
//            RunningState.Paused -> {
//                if (pauseTime == 0L) {
//                    pauseTime = endTime.value
//                    endTime.value += 100000000000L
//                }
//            }
//            else-> {}
//        }
//        delay(1)
//    }
//}
//
//class TimerMy {
//    private lateinit var timer: CountDownTimer
//
//    fun start(sec: Int, endCommand: ()-> Unit) {
//        if (!this::timer.isInitialized) {
//            Looper.prepare()
//            timer = object:CountDownTimer(sec * 1000L, 1000) {
//                override fun onTick(millisUntilFinished: Long) {}
//                override fun onFinish() { endCommand() }
//            }.start()
//        }
//    }
//    fun cancel() { timer.cancel() }
//}


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