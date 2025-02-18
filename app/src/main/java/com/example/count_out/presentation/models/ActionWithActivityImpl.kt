package com.example.count_out.presentation.models

import com.example.domain.entity.ActionWithActivity
import com.example.domain.entity.Activity

data class ActionWithActivityImpl(
    override val exerciseId: Long,
    override val activity: Activity
) : ActionWithActivity