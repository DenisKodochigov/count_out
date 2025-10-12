package com.count_out.presentation.view_element

import com.count_out.domain.entity.enums.ConnectState
import com.count_out.domain.entity.enums.PartName
import com.count_out.presentation.R

data class EnumsTo(val item: Any){
    fun string():Int{
        return when (item){
            ConnectState.NOT_CONNECTED-> {R.string.not_connected}
            ConnectState.CONNECTED-> {R.string.connected}
            ConnectState.CONNECTING-> {R.string.connecting}
            PartName.WorkUp -> R.string.work_up
            PartName.WorkOut -> R.string.work_out
            PartName.WorkDown -> R.string.work_down
            else -> {0}
        }
    }
}
