package com.count_out.presentation.view_element

import com.count_out.domain.entity.enums.ConnectState
import com.count_out.domain.entity.enums.RoundType
import com.count_out.presentation.R

data class EnumsTo(val item: Any){
    fun string():Int{
        return when (item){
            ConnectState.NOT_CONNECTED-> {R.string.not_connected}
            ConnectState.CONNECTED-> {R.string.connected}
            ConnectState.CONNECTING-> {R.string.connecting}
            RoundType.WorkUp -> R.string.work_up
            RoundType.WorkOut -> R.string.work_out
            RoundType.WorkDown -> R.string.work_down
            else -> {0}
        }
    }
}
