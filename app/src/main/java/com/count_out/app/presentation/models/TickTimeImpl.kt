package com.count_out.app.presentation.models

import com.count_out.entity.entity.TickTime

data class TickTimeImpl(
    override val hour: String,
    override val min: String,
    override val sec: String
): TickTime
