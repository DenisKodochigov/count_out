package com.example.count_out.service.stopwatch

import com.example.count_out.domain.pad
import com.example.count_out.entity.StateRunning
import com.example.count_out.entity.TickTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

object StopWatchNew {
    private lateinit var scope: CoroutineScope
    private val currentTickTime: MutableStateFlow<TickTime> =  MutableStateFlow(TickTime())
    private var stateTimer: MutableStateFlow<StateRunning> =  MutableStateFlow(StateRunning.Created)
    private fun engineStopWatch(){
        scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            var currentTime = 0L
            while (stateTimer.value != StateRunning.Stopped){
                if (stateTimer.value == StateRunning.Started){
                    delay(1000L)
                    currentTime += 1
                    currentTickTime.value =  TickTime(
                        hour = ( currentTime / 3600).toInt().pad(),
                        min =  ( currentTime / 60).toInt().pad(),
                        sec =  ( currentTime % 60).toInt().pad(),)
                }
            }
        }
    }
    fun start(state: MutableStateFlow<StateRunning>){
        stateTimer = state
        engineStopWatch()
    }
    fun stop(){ scope.cancel() }
    fun getTickTime(): MutableStateFlow<TickTime> = currentTickTime
}