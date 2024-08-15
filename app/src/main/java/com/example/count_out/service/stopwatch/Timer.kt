package com.example.count_out.service.stopwatch

import com.example.count_out.entity.TimerState
import com.example.count_out.ui.view_components.lg
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class Timer {
    val state: MutableStateFlow <TimerState> = MutableStateFlow(TimerState.END)
    private var interval: Long = 100
    private var startPaused: Long = 0
    private var endCounting: MutableStateFlow <Long> = MutableStateFlow(0L)
    private var startCounting: MutableStateFlow <Long> = MutableStateFlow(0L)
    private fun run() {
         CoroutineScope(Dispatchers.Default).launch {
             while ( endCounting.value > System.currentTimeMillis() || state.value == TimerState.STOPPED){
                 delay( interval) }
             state.value = TimerState.END
         }
    }
    fun changeState(command: TimerState, countMillSec: Long){
        if (state.value == TimerState.COUNTING){
            if (command == TimerState.STOPPED){
                startPaused = System.currentTimeMillis()
                state.value = TimerState.STOPPED
            } else if (command == TimerState.END){ state.value = TimerState.END }
        } else if (state.value == TimerState.STOPPED){
            if (command == TimerState.COUNTING){
                endCounting.value += System.currentTimeMillis() - startPaused
                state.value = TimerState.COUNTING
            } else if (command == TimerState.END){ state.value = TimerState.END }
        } else if (state.value == TimerState.END){
            if (command == TimerState.COUNTING){
                startCounting.value = System.currentTimeMillis()
                endCounting.value =  System.currentTimeMillis() + countMillSec
                interval = calculateInterval(countMillSec)
                state.value = TimerState.COUNTING
                run()
            }
        }
    }

    suspend fun endCounting(callBack: ()-> Unit){
        state.collect{
            if ( it == TimerState.END) {
                lg("End counting timer start: ${startCounting.value} - end: ${endCounting.value} = " +
                        "${endCounting.value - startCounting.value}")
                callBack()
            }
        }
    }

    private fun calculateInterval(countMillSec: Long): Long{
        return if (countMillSec % 5 != 0L) 1L
        else if (countMillSec % 10 != 0L) 5L
        else if (countMillSec % 50 != 0L) 10L
        else if (countMillSec % 100 != 0L) 50L
        else if (countMillSec % 500 != 0L) 100L
        else if (countMillSec % 1000 != 0L) 500L
        else 1000L
    }

    fun state() = state
}
