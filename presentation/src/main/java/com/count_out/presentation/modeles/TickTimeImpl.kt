package com.count_out.presentation.modeles

import com.count_out.domain.entity.TickTime

data class TickTimeImpl(
    override val hour: String,
    override val min: String,
    override val sec: String
): TickTime
