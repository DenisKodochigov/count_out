package com.example.count_out.ui.models

import com.example.count_out.entity.workout.ActionWithSet
import com.example.count_out.entity.workout.Set

data class ActionWithSetImpl (
    override val id: Long,
    override val set: Set
): ActionWithSet