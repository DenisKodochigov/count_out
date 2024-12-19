package com.example.count_out.entity.ui

import com.example.count_out.entity.workout.Set

data class ExecuteInfoSet(
    val currentSet: Set? = null,
    val setNumber: Int = 0,
    val quantitySet: Int = 0,
)
