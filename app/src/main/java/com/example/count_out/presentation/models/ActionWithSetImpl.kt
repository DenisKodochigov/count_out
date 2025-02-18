package com.example.count_out.presentation.models

import com.example.domain.entity.ActionWithSet
import com.example.domain.entity.Set

data class ActionWithSetImpl (
    override val trainingId: Long,
    override val set: Set
): ActionWithSet