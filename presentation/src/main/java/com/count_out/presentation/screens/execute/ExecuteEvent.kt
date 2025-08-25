package com.count_out.presentation.screens.execute

import com.count_out.domain.entity.workout.ShowBottomSheet
import com.count_out.presentation.screens.prime.Event

sealed class ExecuteEvent: Event {
    data object Start: ExecuteEvent()
    data object Pause: ExecuteEvent()
    data class Stop(val item: ShowBottomSheet) : ExecuteEvent()
    data object Save : ExecuteEvent()
    data object Init : ExecuteEvent()

    data object UpInterval: ExecuteEvent()
    data object DownInterval: ExecuteEvent()
    data class ShowBS(val item: ShowBottomSheet): ExecuteEvent()
    data object ToScreenPlans : ExecuteEvent()
    data object BackScreen : ExecuteEvent()
}
