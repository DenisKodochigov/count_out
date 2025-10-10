package com.count_out.domain.entity.supportive

import com.count_out.domain.entity.workout.Domain

data class NameId(
    val name: String,
    val id: Long
): Domain
