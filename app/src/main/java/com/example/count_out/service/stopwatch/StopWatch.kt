package com.example.count_out.service.stopwatch

import androidx.compose.runtime.mutableStateOf
import com.example.count_out.domain.pad
import com.example.count_out.entity.StopwatchState
import java.util.Timer
import javax.inject.Singleton
import kotlin.concurrent.fixedRateTimer
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Singleton
class StopWatch  {
    private var duration: Duration = Duration.ZERO
    private lateinit var timer: Timer
    private var seconds = mutableStateOf("00")
    private var minutes = mutableStateOf("00")
    private var hours = mutableStateOf("00")
    private var currentState = mutableStateOf(StopwatchState.Idle)

    fun onStart(onTick: (h: String, m: String, s: String) -> Unit) {
        currentState.value = StopwatchState.Started
        timer = fixedRateTimer(initialDelay = 1000L, period = 1000L) {
            duration = duration.plus(1.seconds)
            updateTimeUnits()
            onTick(hours.value, minutes.value, seconds.value)
        }
    }
    private fun updateTimeUnits() {
        duration.toComponents { h, m, s, _ ->
           hours.value = h.toInt().pad()
           minutes.value = m.pad()
           seconds.value = s.pad()
        }
    }
    fun cancel() {
        duration = Duration.ZERO
        currentState.value = StopwatchState.Idle
        updateTimeUnits()
    }
    fun onStop() {
        duration = Duration.ZERO
        updateTimeUnits()
    }
}