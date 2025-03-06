package com.count_out.app.device.timer



import com.count_out.domain.entity.enums.RunningState
import com.count_out.domain.entity.pad
import com.count_out.service.service_timing.models.TickTimeImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

object Ticker {
    private val currentTickTime: MutableStateFlow<TickTimeImpl> =  MutableStateFlow(TickTimeImpl())
    private var stateTimer: MutableStateFlow<com.count_out.domain.entity.enums.RunningState?> =  MutableStateFlow(
        com.count_out.domain.entity.enums.RunningState.Binding
    )
    private fun engineWatcher(){
        CoroutineScope(Dispatchers.Default).launch {
            var currentTime = 0L
            while (stateTimer.value != com.count_out.domain.entity.enums.RunningState.Stopped){
                if (stateTimer.value == com.count_out.domain.entity.enums.RunningState.Started){
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
    fun start(state: MutableStateFlow<com.count_out.domain.entity.enums.RunningState?>){
        stateTimer = state
        currentTickTime.value = TickTimeImpl(hour = "00", min = "00", sec = "00")
        engineWatcher()
    }
    fun stop(){ currentTickTime.value = TickTimeImpl(hour = "00", min = "00", sec = "00")
    }
    fun getTickTime(): MutableStateFlow<TickTimeImpl> = currentTickTime
}
