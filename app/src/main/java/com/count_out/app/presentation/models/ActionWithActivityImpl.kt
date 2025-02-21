package com.count_out.app.presentation.models

import com.count_out.domain.entity.ActionWithActivity
import com.count_out.domain.entity.Activity

data class ActionWithActivityImpl(
    override val exerciseId: Long,
    override val activity: Activity
) : ActionWithActivity