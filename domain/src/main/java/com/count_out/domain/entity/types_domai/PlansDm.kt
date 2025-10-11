package com.count_out.domain.entity.types_domai

import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.Plan

@JvmInline
value class PlansDm(val item: List<Plan>): Domain