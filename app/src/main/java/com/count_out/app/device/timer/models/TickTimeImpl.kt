package com.count_out.app.device.timer.models

import com.count_out.app.entity.workout.TickTime

data class TickTimeImpl(
    override val hour: String="00",
    override val min: String="00",
    override val sec: String="00",
): TickTime
