package com.count_out.data.models

import com.count_out.entity.entity.workout.ActionWithSet
import com.count_out.entity.entity.workout.Set

data class ActionWithSetImpl(
    override val id: Long,
    override val set: Set
): ActionWithSet
