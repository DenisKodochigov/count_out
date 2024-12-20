package com.example.count_out.entity.ui

import com.example.count_out.entity.workout.Set

data class ExecuteInfoSet(
    val currentSet: Set? = null,
    val numberSet: Int = 0,
    val quantitySet: Int = 0,
)
