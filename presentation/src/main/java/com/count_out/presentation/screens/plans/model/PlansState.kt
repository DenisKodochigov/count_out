package com.count_out.presentation.screens.plans.model

import com.count_out.domain.entity.router.DeviceBle
import com.count_out.domain.entity.workout.Collapsing
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.Plan
import com.count_out.domain.entity.workout.Selecting
import com.count_out.presentation.models.BottomSheetInterface
import com.count_out.presentation.models.LauncherBSp
import com.count_out.presentation.screens.prime.Event

data class PlansState(
    val plans: List<Plan> = emptyList(),
    var selectedId: Long? = null,
    val launcherBS: LauncherBSp = LauncherBSp(),
    val collapsing: Collapsing = Collapsing(),
    var selecting: Selecting = Selecting(),
    var goToScreenExecuteWorkout: ()->Unit = {},
    var goToScreenPlan: (Long)->Unit = {},

    override var list: List<Domain> = emptyList(),
    override var nameSection: String = "",
    override var item: Domain? = null,
    override val devicesUI: List<DeviceBle> = emptyList(),
    override var onConfirmation: (Domain) -> Unit = {},
    override var onDismiss: () -> Unit = {},
    override val event: (Event) -> Unit ={},
): BottomSheetInterface