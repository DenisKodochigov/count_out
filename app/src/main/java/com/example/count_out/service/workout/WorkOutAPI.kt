package com.example.count_out.service.workout

import com.example.count_out.entity.SendToUI
import com.example.count_out.entity.SendToWorkService

interface WorkOutAPI {
    var sendToUI: SendToUI?
    var sendToWork: SendToWorkService?

    fun startWorkout()
    fun stopWorkout()
    fun pauseWorkout()
    fun continueWorkout()
}