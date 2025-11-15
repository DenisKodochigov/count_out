package com.count_out.presentation.screens.execute

import com.count_out.presentation.models.LauncherBSp
import com.count_out.presentation.screens.prime.Event

sealed class ExecuteEvent: Event {
    data object Start: ExecuteEvent()
    data object Pause: ExecuteEvent()
    data object Stop : ExecuteEvent()
    data object Save : ExecuteEvent()
    data object UpInterval: ExecuteEvent()
    data object DownInterval: ExecuteEvent()
    data class Launcher(val item: LauncherBSp): ExecuteEvent()
}
