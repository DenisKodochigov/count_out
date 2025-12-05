package com.count_out.domain.entity.router

import com.count_out.domain.entity.StepPlan
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.Plan

interface ForWork: Domain {
    val plan: Plan
    val enableSpeechDescription: Boolean
    val idSetChangeInterval: Long
    val interval: Double
    val cancelCoroutineWork: ()-> Unit
    val map: List<StepPlan>
    val exerciseCount: Int
}


