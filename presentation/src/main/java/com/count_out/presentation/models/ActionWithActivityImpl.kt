package com.count_out.presentation.models

import com.count_out.domain.entity.workout.ActionWithActivity
import com.count_out.domain.entity.workout.Activity


data class ActionWithActivityImpl(
    override val exerciseId: Long,
    override val activity: Activity
) : ActionWithActivity