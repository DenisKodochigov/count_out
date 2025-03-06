package com.count_out.service.service_timing.models

data class TickTimeImpl(
    override val hour: String = "00",
    override val min: String = "00",
    override val sec: String = "00",
): com.count_out.domain.entity.TickTime
