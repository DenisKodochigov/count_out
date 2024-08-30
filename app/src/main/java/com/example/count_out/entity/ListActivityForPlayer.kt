package com.example.count_out.entity

data class ListActivityForPlayer(
    val roundNameId: Int = 0,
    val roundName: String = "",
    val activityName: String ="",
    val setDescription: String = "",
    val countSet: Int = 0,
    val currentSet: Int = 0,
    val countRing: Int = 0,
    val currentRing: Int = 0,
)
