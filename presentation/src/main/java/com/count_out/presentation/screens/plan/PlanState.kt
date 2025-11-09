package com.count_out.presentation.screens.plan

import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.entity.workout.Collapsing
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.Plan
import com.count_out.domain.entity.workout.Selecting
import com.count_out.presentation.models.BottomSheetInterface
import com.count_out.presentation.models.LauncherBSp
import com.count_out.presentation.screens.prime.Event

data class PlanState (
    val plan: Plan? = null,
//    val showBS: ShowBottomSheet = ShowBottomSheet(),
    val launcherBS: LauncherBSp = LauncherBSp().element(emptyList()).type(null),
    val collapsing: Collapsing = Collapsing(),
    var selecting: Selecting = Selecting(),
    var goToScreenPlans: ()->Unit = {},

    override var activities: List<Activity> = emptyList(),
    override var nameSection: String = "",
    override var item: Domain? = null,
    override var onConfirmation: (Domain) -> Unit = {},
    override var onDismiss: () -> Unit = {},
    override val event: (Event) -> Unit ={},
): BottomSheetInterface
