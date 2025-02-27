package com.count_out.app.modeles

import com.count_out.data.entity.TickTime

data class TickTimeImpl(
    override val hour: String,
    override val min: String,
    override val sec: String
): TickTime