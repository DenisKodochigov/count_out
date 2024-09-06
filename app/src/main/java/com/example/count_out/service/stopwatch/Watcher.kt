package com.example.count_out.service.stopwatch

import com.example.count_out.domain.pad
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.TickTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

object Watcher {
    private lateinit var scope: CoroutineScope
    private val currentTickTime: MutableStateFlow<TickTime> =  MutableStateFlow(TickTime())
    private var stateTimer: MutableStateFlow<RunningState> =  MutableStateFlow(RunningState.Stopped)
    private fun engineWatcher(){
        scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            var currentTime = 0L
            while (stateTimer.value != RunningState.Stopped){
                if (stateTimer.value == RunningState.Started){
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
    fun start(state: MutableStateFlow<RunningState>){
        stateTimer = state
        engineWatcher()
    }
    fun stop(){
        currentTickTime.value = TickTime()
        scope.cancel()
    }
    fun getTickTime(): MutableStateFlow<TickTime> = currentTickTime
}
