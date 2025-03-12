package com.count_out.presentation.models

import com.count_out.domain.entity.workout.ActionWithSet
import com.count_out.domain.entity.workout.Set

data class ActionWithSetImpl (
    override val id: Long,
    override val set: Set
): ActionWithSet