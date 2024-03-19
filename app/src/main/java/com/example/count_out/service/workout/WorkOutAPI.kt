package com.example.count_out.service.workout

import com.example.count_out.entity.TickTime
import com.example.count_out.entity.Training
import com.example.count_out.entity.VariablesInService
import com.example.count_out.entity.VariablesOutService
import kotlinx.coroutines.flow.MutableStateFlow

interface WorkOutAPI {
    var flowTick: MutableStateFlow<TickTime>
    var flowOutMut: MutableStateFlow<VariablesOutService>
    var variablesIn: VariablesInService
    val training: MutableStateFlow<Training>

    suspend fun startWorkout()
    fun stopWorkout()
    fun pauseWorkout()
}