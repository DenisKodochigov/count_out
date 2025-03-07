package com.example.count_out.entity.models

import com.example.count_out.entity.workout.ActionWithActivity
import com.example.count_out.entity.workout.Activity

data class ActionWithActivityImpl(
    override val exerciseId: Long,
    override val activity: Activity
) : ActionWithActivity