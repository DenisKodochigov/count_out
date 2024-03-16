package com.example.count_out.entity

data class StateWorkOut (
    val time: Long? = System.currentTimeMillis(),
    val state: String? = null,
    val set: Set? = null,
)
