package com.example.count_out.service.workout

import com.example.count_out.entity.SendToWorkService
import com.example.count_out.entity.SendToUI

interface WorkOutAPI {
    var variablesOut: SendToUI
    var variablesIn: SendToWorkService

    fun startWorkout()
    fun stopWorkout()
    fun pauseWorkout()
    fun continueWorkout()
}