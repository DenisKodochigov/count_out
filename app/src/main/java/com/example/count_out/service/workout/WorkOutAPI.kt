package com.example.count_out.service.workout

import com.example.count_out.entity.VariablesInService
import com.example.count_out.entity.VariablesOutService

interface WorkOutAPI {
    var variablesOut: VariablesOutService
    var variablesIn: VariablesInService

    fun startWorkout()
    fun stopWorkout()
    fun pauseWorkout()
    fun continueWorkout()
}