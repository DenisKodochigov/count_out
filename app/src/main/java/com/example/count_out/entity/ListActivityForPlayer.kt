package com.example.count_out.entity

data class ListActivityForPlayer(
    val roundName: String = "",
    val activityName: String ="",
    val typeDescription: Boolean? = null,
    val countSet: Int = 0,
    val currentSet: Int = 0,
    val countRing: Int = 0,
    val currentRing: Int = 0,
)
