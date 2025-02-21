package com.count_out.app.device.timer

import com.count_out.app.device.timer.models.TickTimeImpl
import com.count_out.domain.entity.enums.RunningState
import com.count_out.domain.entity.pad
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

object Ticker {
    private val currentTickTime: MutableStateFlow<TickTimeImpl> =  MutableStateFlow(TickTimeImpl())
    private var stateTimer: MutableStateFlow<RunningState?> =  MutableStateFlow(RunningState.Binding)
    private fun engineWatcher(){
        CoroutineScope(Dispatchers.Default).launch {
            var currentTime = 0L
            while (stateTimer.value != RunningState.Stopped){
                if (stateTimer.value == RunningState.Started){
                    delay(1000L)
                    currentTime += 1
                    currentTickTime.value = TickTimeImpl(
                        hour = (currentTime / 3600).toInt().pad(),
                        min = (currentTime / 60).toInt().pad(),
                        sec = (currentTime % 60).toInt().pad(),
                    )
                }
            }
        }
    }
    fun start(state: MutableStateFlow<RunningState?>){
        stateTimer = state
        currentTickTime.value = TickTimeImpl(hour = "00", min = "00", sec = "00")
        engineWatcher()
    }
    fun stop(){ currentTickTime.value = TickTimeImpl(hour = "00", min = "00", sec = "00")
    }
    fun getTickTime(): MutableStateFlow<TickTimeImpl> = currentTickTime
}
