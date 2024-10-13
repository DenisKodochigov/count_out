package com.example.count_out.entity.workout

import com.example.count_out.entity.TickTime

data class MessageWorkOut(
    val message: String = "",
    val tickTime: TickTime = TickTime(),
)
