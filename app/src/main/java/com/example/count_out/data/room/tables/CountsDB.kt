package com.example.count_out.data.room.tables

import com.example.count_out.entity.no_use.Count
import com.example.count_out.entity.no_use.Counts

data class CountsDB(
    override val counts: List<Count> = emptyList()
): Counts
