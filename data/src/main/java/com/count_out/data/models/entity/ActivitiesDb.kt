package com.count_out.data.models.entity

import com.count_out.data.models.Data
import com.count_out.domain.entity.workout.Activities
import com.count_out.domain.entity.workout.Activity

abstract class ActivitiesDb: Data {
    abstract val activities: List<ActivityDb>
    override fun toDomain(ind: Int) = object: Activities {
        override val activities: List<Activity> = this@ActivitiesDb.activities.map { it.toDomain(0) }
    }
}