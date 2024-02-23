package com.example.count_out.data.room.tables

import com.example.count_out.entity.StateWorkOut

data class StateWorkOutDB(
    override val time: Long? = System.currentTimeMillis(),
    override val state: String? = null
): StateWorkOut
