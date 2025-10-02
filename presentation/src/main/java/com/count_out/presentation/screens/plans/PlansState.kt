package com.count_out.presentation.screens.plans

import com.count_out.domain.entity.workout.Plan
import com.count_out.presentation.screens.prime.DataState
import com.count_out.presentation.screens.prime.Event

data class PlansState(
    val plans: List<Plan> = emptyList(),
    var selectedId: Long? = null,
    var goToScreenExecuteWorkout: ()->Unit = {},
    var goToScreenTraining: (Long)->Unit = {},
    override val event:(Event) -> Unit,
): DataState