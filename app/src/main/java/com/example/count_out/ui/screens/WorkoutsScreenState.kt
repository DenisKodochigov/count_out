package com.example.count_out.ui.screens

import com.example.count_out.entity.Workout

data class WorkoutsScreenState(
    val baskets: List<Workout> = emptyList(),
    val refresh: Boolean
)