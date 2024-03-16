package com.example.count_out.service.workout

import com.example.count_out.entity.StateWorkOut
import com.example.count_out.entity.TickTime
import com.example.count_out.entity.Training
import kotlinx.coroutines.flow.MutableStateFlow

interface WorkOutAPI {
    var flowTick: MutableStateFlow<TickTime>
    var flowStateService: MutableStateFlow<StateWorkOut>
    val training: MutableStateFlow<Training>

    suspend fun startWorkout()
    fun stopWorkout()
    fun pauseWorkout()
}