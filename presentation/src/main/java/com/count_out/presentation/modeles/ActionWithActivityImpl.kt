package com.count_out.presentation.modeles

import com.count_out.domain.entity.workout.ActionWithActivity
import com.count_out.domain.entity.workout.Activity


data class ActionWithActivityImpl(
    override val exerciseId: Long,
    override val activity: Activity
) : ActionWithActivity