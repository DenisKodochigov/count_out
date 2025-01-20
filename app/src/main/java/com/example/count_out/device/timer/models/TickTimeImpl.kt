package com.example.count_out.device.timer.models

import com.example.count_out.entity.workout.TickTime

data class TickTimeImpl(
    override val hour: String="00",
    override val min: String="00",
    override val sec: String="00",
): TickTime
