package com.count_out.domain.entity

import com.count_out.domain.entity.workout.Domain

class SetViewId (
    val idOwner: Long = 0,
    val owner: Domain? = null,
    val from: Int = 0,
    val to: Int = 0,
): Domain