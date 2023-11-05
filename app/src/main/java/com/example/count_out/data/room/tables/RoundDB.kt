package com.example.count_out.data.room.tables

import com.example.count_out.entity.Round
import com.example.count_out.entity.Set

data class RoundDB(
    override val set: Set = SetDB(),
    override val amount: Int = 10,
    override val beforeTime: Int = 5,
    override val afterTime: Int = 5,
    override val restTime: Int = 4,
    override val open: Boolean = false
): Round
