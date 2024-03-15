package com.example.count_out.service.stopwatch

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.count_out.domain.pad
import com.example.count_out.entity.TickTime
import java.util.Timer
import kotlin.concurrent.fixedRateTimer
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class StopWatch {
    private var duration: Duration = Duration.ZERO
    private lateinit var timer: Timer
    private val pauseStopWatch = mutableStateOf(false)

    fun onStart(onTick: (TickTime) -> Unit) {
        timer = fixedRateTimer(initialDelay = 1000L, period = 1000L) {
            if (!pauseStopWatch.value) {
                duration = duration.plus(1.seconds)
                duration.toComponents { h, m, s, _ ->
                    onTick(TickTime(hour = h.toInt().pad(), min = m.pad(), sec = s.pad()))
                }
            }
        }
    }
    fun onStop() {
        duration = Duration.ZERO
        timer.cancel()
    }
    fun onPause(pause: MutableState<Boolean>) {
        pauseStopWatch.value = pause.value
    }
}