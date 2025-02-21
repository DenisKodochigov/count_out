package com.count_out.app.presentation.models

import com.count_out.domain.entity.ActionWithSet
import com.count_out.domain.entity.Set

data class ActionWithSetImpl (
    override val id: Long,
    override val set: Set
): ActionWithSet