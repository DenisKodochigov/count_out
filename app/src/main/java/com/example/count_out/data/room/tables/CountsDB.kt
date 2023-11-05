package com.example.count_out.data.room.tables

import com.example.count_out.entity.Count
import com.example.count_out.entity.Counts

data class CountsDB(
    override val counts: List<Count> = emptyList()
): Counts
