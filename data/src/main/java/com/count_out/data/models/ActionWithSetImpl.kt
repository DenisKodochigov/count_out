package com.count_out.data.models

import com.count_out.domain.entity.ActionWithSet
import com.count_out.domain.entity.Set

data class ActionWithSetImpl(
    override val id: Long,
    override val set: Set
): ActionWithSet
